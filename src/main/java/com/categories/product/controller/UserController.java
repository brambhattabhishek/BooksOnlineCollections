package com.categories.product.controller;

import com.categories.product.dto.userDTO.UserRequestDTO;
import com.categories.product.dto.userDTO.UserResponseDTO;
import com.categories.product.entities.User;
import com.categories.product.security.JwtUtil;
import com.categories.product.serviceImpl.CustomUserDetailsService;
import com.categories.product.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    private final MyUserDetailsService userService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(
            MyUserDetailsService userService,
            JwtUtil jwtUtil,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @RequestBody UserRequestDTO userRequestDTO
    ) {
        // 1️⃣ Save user with default role if not provided
        UserResponseDTO responseDTO = userService.registerUser(userRequestDTO);

        // 🔹 Ensure role is set (default SELLER)
        if (responseDTO.getRoles() == null || responseDTO.getRoles().isEmpty()) {
            responseDTO.setRoles(List.of("ROLE_SELLER"));
        }

        // 2️⃣ Load UserDetails (so that roles can be fetched correctly)
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(responseDTO.getUsername());

        // 3️⃣ Extract roles from UserDetails
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 4️⃣ Generate JWT token
        String token = jwtUtil.generateToken(userDetails.getUsername(), roles);

        // 5️⃣ Set token & roles in response
        responseDTO.setToken(token);
        responseDTO.setRoles(roles);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    // ✅ Login User
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(
            @RequestBody UserRequestDTO userRequestDTO
    ) {

        // 🔐 Authenticate user (calls CustomUserDetailsService internally)
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userRequestDTO.getUsername(),
                                userRequestDTO.getPassword()
                        )
                );

        // 🔹 Extract roles from Authentication
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 🔑 Generate JWT
        String token =
                jwtUtil.generateToken(
                        authentication.getName(),
                        roles
                );
        User user = customUserDetailsService.getUserEntityByUsername(authentication.getName());

        // 🔁 Prepare response
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setUsername(authentication.getName());
        responseDTO.setToken(token);
        responseDTO.setRoles(roles);

        return ResponseEntity.ok(responseDTO);
    }
}
