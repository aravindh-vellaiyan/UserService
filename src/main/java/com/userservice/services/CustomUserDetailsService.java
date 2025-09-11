package com.userservice.services;

import com.userservice.models.User;
import com.userservice.repositories.UserRepository;
import com.userservice.security.models.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found for the username : " + username);
        }
        return new CustomUserDetails(user.get());
    }
}
