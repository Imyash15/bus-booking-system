package com.bookingService.exception;

public class RouteException extends RuntimeException{

    public RouteException(String message){
        super(message);
    }

    public RouteException(){}
}
