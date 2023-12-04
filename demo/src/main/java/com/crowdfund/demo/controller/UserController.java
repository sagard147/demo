package com.crowdfund.demo.controller;

import com.crowdfund.demo.mapper.*;
import com.crowdfund.demo.model.*;
import com.crowdfund.demo.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(path = "api/v1/users")
public class UserController {
    private final UserAuthService userAuthService;

    @Autowired
    public UserController(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listAllProject(@RequestParam Map<String, String> reqParam){
        // Filters search param & ProjectType
        List<User> user = userAuthService.ListUser();;
        return new ResponseEntity<List<User>>(user, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getProject(@PathVariable("userId") long userId){
        try{
            User user = userAuthService.getUserById(userId);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/signin")
    public ResponseEntity<UserDTO> signin(@RequestParam long userId, @RequestParam String password) {
        try {
            UserDTO userDTO = userAuthService.signin(userId, password);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDTO) {
        try {
            System.out.println(userDTO);
            UserDTO createdUser = userAuthService.signup(userDTO);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
