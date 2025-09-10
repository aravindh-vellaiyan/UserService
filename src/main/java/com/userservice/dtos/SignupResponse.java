package com.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private long id;
    private String name;
    private String email;
    private boolean emailVerified;
}