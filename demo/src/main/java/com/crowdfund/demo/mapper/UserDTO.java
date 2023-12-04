package com.crowdfund.demo.mapper;

import com.crowdfund.demo.model.Role;
import com.crowdfund.demo.model.Roles;
import com.crowdfund.demo.model.User;
import com.crowdfund.demo.model.UserRole;

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Role accountType;

    // Constructors, getters, and setters

    public UserDTO() {
        // Default constructor
    }

    public UserDTO(Long id, String name, String email, Role accountType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.accountType = accountType;
    }

    // Other getters and setters

    // For sign-up, include password in the DTO

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        // Convert the String to Role enum
        this.accountType = Role.valueOf(accountType);
    }

    public static UserDTO toUserDTO(User user) {
        // Convert User entity to UserDTO
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(user.getId());
        newUserDTO.setName(user.getName());
        newUserDTO.setEmail(user.getEmail());
        return newUserDTO;
    }

    public static UserDTO toUserDTO(User user, Roles role) {
        UserDTO newUserDTO = toUserDTO(user); // Reuse the logic from the other method

        if (role != null) {
            newUserDTO.setAccountType(role.getRole().toString());
        }

        return newUserDTO;
    }

    // Optional: You may want to override toString() for logging or debugging
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", accountType='" + accountType + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

