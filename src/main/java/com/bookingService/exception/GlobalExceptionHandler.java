package com.bookingService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleUserException(ResourceNotFoundException e, WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),false, e.getMessage(),request.getDescription(false),new ArrayList<>());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
