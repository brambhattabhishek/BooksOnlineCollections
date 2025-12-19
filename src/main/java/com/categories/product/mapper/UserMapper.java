package com.categories.product.mapper;

import com.categories.product.dto.userDTO.UserRequestDTO;
import com.categories.product.dto.userDTO.UserResponseDTO;
import com.categories.product.entities.User;
import com.categories.product.entities.Role;

import java.util.List;

public class UserMapper {

    // UserRequestDTO → User entity
    public static User toEntity(UserRequestDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());

        // Set role from DTO, default to SELLER
        user.setRole(dto.getRole() != null ? dto.getRole() : Role.SELLER);

        return user;
    }

    // User entity → UserResponseDTO
    public static UserResponseDTO toResponseDTO(User user) {
        if (user == null) return null;

        UserResponseDTO response = new UserResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getUsername());

        // Include role in response
        if (user.getRole() != null) {
            response.setRoles(List.of("ROLE_" + user.getRole().name()));
        }

        return response;
    }
}
