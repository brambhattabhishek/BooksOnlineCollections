package com.categories.product.serviceImpl;

import com.categories.product.dto.userDTO.UserRequestDTO;
import com.categories.product.dto.userDTO.UserResponseDTO;
import com.categories.product.entities.User;
import com.categories.product.mapper.UserMapper;
import com.categories.product.repositories.UserRepository;
import com.categories.product.services.MyUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MyUserDetailsServiceImpl(UserRepository userRepository,
                                    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto) {

        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserMapper.toResponseDTO(userRepository.save(user));
    }
}
