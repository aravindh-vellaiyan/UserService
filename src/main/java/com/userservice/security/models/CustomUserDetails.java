package com.userservice.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.userservice.models.Role;
import com.userservice.models.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@JsonDeserialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private User user;

    private List<GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles()
                .stream()
                .map(CustomGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    @JsonProperty("username")
    public String getUsername() {
        return user.getEmail();
    }

    public void setAuthorities(List<CustomGrantedAuthority> authorities){
        if(authorities != null && !authorities.isEmpty()) {
            List<Role> roles = new LinkedList<>();
            for(CustomGrantedAuthority authority : authorities){
                Role role = new Role();
                role.setName(authority.getAuthority());
                roles.add(role);
            }
            user.setRoles(roles);
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
