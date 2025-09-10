package com.userservice.advices;

import com.userservice.dtos.MessageResponse;
import com.userservice.exceptions.AuthenticationException;
import com.userservice.exceptions.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<MessageResponse> handleUserException(UserException userException){
        MessageResponse errorResponse = new MessageResponse();
        errorResponse.setMessage(userException.getMessage());
        return new ResponseEntity<MessageResponse>(errorResponse, HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<MessageResponse> handleAuthenticationException(AuthenticationException authException){
        MessageResponse errorResponse = new MessageResponse();
        errorResponse.setMessage(authException.getMessage());
        return new ResponseEntity<MessageResponse>(errorResponse, HttpStatus.OK);
    }
}
