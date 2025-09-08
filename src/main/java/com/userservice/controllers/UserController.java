package com.userservice.controllers;

import com.userservice.dtos.LoginRequest;
import com.userservice.dtos.LogoutRequest;
import com.userservice.dtos.LogoutResponse;
import com.userservice.dtos.SignupRequest;
import com.userservice.models.Token;
import com.userservice.models.User;
import com.userservice.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest signupRequest){
        return userService.signup(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getPassword());
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping("/logout")
    public LogoutResponse logout(@RequestBody LogoutRequest logoutRequest){
        userService.invalidateToken(logoutRequest.getToken());
        LogoutResponse response = new LogoutResponse();
        response.setMessage("Session logged out successfully.!");
        return response;
    }
}
