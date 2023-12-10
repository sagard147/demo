package com.crowdfund.demo.controller;

import com.crowdfund.demo.mapper.*;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS })
public class UserController {
    private final IUserAuthService userAuthService;

    @Autowired
    public UserController(IUserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listAllUsers(@RequestParam Map<String, String> reqParam){
        // Filters search param & ProjectType
        List<User> user = userAuthService.ListUser();;
        return new ResponseEntity<List<User>>(user, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") long userId){
        User user = userAuthService.getUserById(userId);
        return new ResponseEntity<User>(user, HttpStatus.OK);

    }
    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signin(@RequestBody Map<String, Object> requestBody) {
        String emailId = (String) requestBody.get("emailId");
        String password = (String) requestBody.get("password");
        UserDTO userDTO = userAuthService.signin(emailId, password);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userAuthService.signup(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}
