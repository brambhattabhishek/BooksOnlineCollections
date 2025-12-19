package com.categories.product.dto.userDTO;

import com.categories.product.entities.Role;

public class UserRequestDTO {

    private String username;
    private String password;
    private Role role;   // 🔥 ADD THIS

    public UserRequestDTO() {}

    public UserRequestDTO(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
