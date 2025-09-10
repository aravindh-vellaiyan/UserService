package com.userservice.controllers;

import com.userservice.dtos.*;
import com.userservice.exceptions.UserException;
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
    public SignupResponse signup(@RequestBody SignupRequest signupRequest){
        return toSignupResponseDTO(userService.signup(signupRequest.getEmail(), signupRequest.getName(), signupRequest.getPassword()));
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/logout")
    public MessageResponse logout(@RequestBody LogoutRequest logoutRequest){
        userService.invalidateToken(logoutRequest.getToken());
        MessageResponse response = new MessageResponse();
        response.setMessage("Session logged out successfully.!");
        return response;
    }

    @PostMapping("/closeaccount")
    public MessageResponse closeAccount(){
//        userService.closeUserAccount();
//        return null;
        throw new UserException("Unsupported Operation");
    }

    @GetMapping("/validate/{token}")
    public User validateToken(@PathVariable String token){
        return userService.validateToken(token);
    }

    private SignupResponse toSignupResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        SignupResponse response = new SignupResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setEmailVerified(user.isEmailVerified());
        return response;
    }
}
