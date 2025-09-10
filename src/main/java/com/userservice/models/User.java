package com.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "User",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class User extends BaseModel{
    private String name;
    @ManyToMany
    private List<Role> roles;
    @JsonIgnore
    private String hashedPassword;
    private String email;
    private boolean isEmailVerified;
}
