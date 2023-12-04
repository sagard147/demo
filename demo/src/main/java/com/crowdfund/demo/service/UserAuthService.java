package com.crowdfund.demo.service;

import com.crowdfund.demo.exception.ApiRequestException;
import com.crowdfund.demo.mapper.*;
import com.crowdfund.demo.model.Role;
import com.crowdfund.demo.model.Roles;
import com.crowdfund.demo.model.User;
import com.crowdfund.demo.model.UserRole;
import com.crowdfund.demo.repository.RolesRepository;
import com.crowdfund.demo.repository.UserRepository;
import com.crowdfund.demo.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserAuthService {

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;


    RolesRepository rolesRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository, UserRoleRepository userRoleRepository, RolesRepository rolesRepository){
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolesRepository = rolesRepository;
    }

    public List<User> ListUser(){
        return userRepository.findAll();
    }

    public User getUserById(long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new ApiRequestException("User Not Found");
        }
        return userOptional.orElse(null);
    }

    public UserDTO signin(long userId, String password) {
        // Implement authentication logic (e.g., check credentials against the database)
        // Throw AuthenticationException if authentication fails
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalMonitorStateException("User not found"));

        if (!password.equals(user.getPassword())) {
            throw new IllegalMonitorStateException("Incorrect password");
        }


        return toUserDTO(user);
    }

    public UserDTO signup(UserDTO userDTO) {
        System.out.println(userDTO.toString());
        String encodedPassword = userDTO.getPassword();
        Roles role= rolesRepository.findByRole(userDTO.getAccountType());

        User user = new User(userDTO.getName(), userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        System.out.println(user.toString());
        userRepository.save(user);

        UserRole userRole = new UserRole(user,role);
        userRoleRepository.save(userRole);

        return toUserDTO(user);
    }

    private UserDTO toUserDTO(User user) {
        // Convert User entity to UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}
