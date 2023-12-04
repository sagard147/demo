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
import com.crowdfund.demo.util.Logger;
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
        Logger.log("Sign-in Entered "+userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiRequestException("User not found"));

        if (!password.equals(user.getPassword())) {
            throw new ApiRequestException("Incorrect password");
        }
        UserRole userRole = userRoleRepository.findByUserId(userId);
        Logger.log("User : " + userId);
        Logger.log("Sign-in successful ");
        return UserDTO.toUserDTO(user,userRole.getRole());
    }

    public UserDTO signup(UserDTO userDTO) {
        Logger.log("SignUp Entered "+userDTO.toString());
        String encodedPassword = userDTO.getPassword();
        Roles role= rolesRepository.findByRole(userDTO.getAccountType());

        User user = new User(userDTO.getName(), userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        Logger.log(user.toString());
        userRepository.save(user);

        UserRole userRole = new UserRole(user,role);
        userRoleRepository.save(userRole);

        userDTO = UserDTO.toUserDTO(user,role);
        userDTO.setAccountType(userDTO.getAccountType().toString());
        Logger.log("SignUp successful ");
        return userDTO;
    }
}
