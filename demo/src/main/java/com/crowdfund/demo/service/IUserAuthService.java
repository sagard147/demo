package com.crowdfund.demo.service;

import com.crowdfund.demo.mapper.UserDTO;
import com.crowdfund.demo.model.User;

import java.util.List;

public interface  IUserAuthService {
    List<User> ListUser();

    User getUserById(long userId);

    UserDTO signin(String emailId, String password);

    UserDTO signup(UserDTO userDTO);
}
