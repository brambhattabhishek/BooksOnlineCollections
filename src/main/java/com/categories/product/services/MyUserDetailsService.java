package com.categories.product.services;

import com.categories.product.dto.userDTO.UserRequestDTO;
import com.categories.product.dto.userDTO.UserResponseDTO;

public interface MyUserDetailsService {

    UserResponseDTO registerUser(UserRequestDTO userRequestDTO);
}
