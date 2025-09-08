package com.userservice.services;

import com.userservice.dtos.LoginRequest;
import com.userservice.dtos.SignupRequest;
import com.userservice.exceptions.AuthenticationException;
import com.userservice.exceptions.UserException;
import com.userservice.models.Token;
import com.userservice.models.User;
import com.userservice.repositories.TokenRepository;
import com.userservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final Random randomGenerator = new Random(2589321423984L);

    public UserService(UserRepository userRepository, TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public User signup(String email, String name, String password){
        if(this.userRepository.findByEmail(email) != null) {
            throw new UserException("User already exist for the Email :" + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(password);
        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        User user = this.userRepository.findByEmail(email);
        if (user != null && user.getHashedPassword().equals(password)){
            Token token = new Token();
            token.setUser(user);
            return this.tokenRepository.save(token);
        }
        throw new AuthenticationException("Check the user email or password.!");
    }

    public void invalidateToken(String token) {
        Token tokenObj;
        if((tokenObj = tokenRepository.findByToken(token)) != null){
            tokenRepository.delete(tokenObj);
            return;
        }
        throw new AuthenticationException("Invalid token");
    }
}
