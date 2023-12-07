package com.crowdfund.demo.mapper;

import com.crowdfund.demo.model.Role;
import com.crowdfund.demo.model.Roles;
import com.crowdfund.demo.model.User;
import com.crowdfund.demo.model.UserRole;

public class UserDTO {

    private Long id;
    private String firstName;

    private String lastName;
    private String email;
    private String bio;
    private String address;
    private String password;
    private Role accountType;

    // Constructors, getters, and setters

    public UserDTO() {
        // Default constructor
    }

    public UserDTO(Long id, String name, String lastName,String email, Role accountType, String bio, String address) {
        this.id = id;
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
        this.accountType = accountType;
        this.bio = bio;
        this.address = address;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static UserDTO toUserDTO(User user) {
        // Convert User entity to UserDTO
        UserDTO newUserDTO = new UserDTO();
        newUserDTO.setId(user.getId());
        newUserDTO.setFirstName(user.getFirstName());
        newUserDTO.setLastName(user.getLastName());
        newUserDTO.setEmail(user.getEmail());
        newUserDTO.setBio(user.getBio());
        newUserDTO.setAddress(user.getAddress());
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
                ", name='" + firstName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

