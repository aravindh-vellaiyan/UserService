package com.userservice.services;

import com.userservice.exceptions.AuthenticationException;
import com.userservice.exceptions.UserException;
import com.userservice.models.Token;
import com.userservice.models.User;
import com.userservice.repositories.TokenRepository;
import com.userservice.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.text.RandomStringGenerator;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange(new char[][] {{'a', 'z'}, {'A', 'Z' }})
            .build();

    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(String email, String name, String password){
        if(this.userRepository.findByEmail(email) != null) {
            throw new UserException("User already exist for the Email :" + email);
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public Token login(String email, String password) {
        User user = this.userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getHashedPassword())){
            Token token = new Token();
            token.setUser(user);
            token.setToken(generator.generate(128));
            LocalDate localDate = LocalDate.now();
            LocalDate oneDayLater = localDate.plusDays(1);
            token.setExpiryAt(Date.from(oneDayLater.atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime());
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

    public User validateToken(String token) {
        Token tokenObj = tokenRepository.findByTokenAndExpiryAtGreaterThan(token, System.currentTimeMillis());
        if(tokenObj == null){
            throw new AuthenticationException("Invalid Token");
        }
        return tokenObj.getUser();
    }
}
