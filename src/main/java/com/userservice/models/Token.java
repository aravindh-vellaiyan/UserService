package com.userservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
        name = "token",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"token"})
        }
)
public class Token extends BaseModel {
    private String token;
    @ManyToOne
    @JsonIgnore
    private User user;
    private long expiryAt;

    @PrePersist
    public void generateToken() {
        if (token == null) {
            token = UUID.randomUUID().toString();
        }
        if(expiryAt == 0) {
            expiryAt = System.currentTimeMillis() + (60 * 60 * 1000);
        }
    }
}
