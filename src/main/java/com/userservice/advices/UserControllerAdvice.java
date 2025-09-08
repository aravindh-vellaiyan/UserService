package com.userservice.advices;

import com.userservice.dtos.ErrorResponse;
import com.userservice.exceptions.AuthenticationException;
import com.userservice.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorResponse> handleUserException(UserException userException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(userException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException authException){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(authException.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}
