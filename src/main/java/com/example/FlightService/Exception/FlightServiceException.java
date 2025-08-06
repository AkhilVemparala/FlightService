package com.example.FlightService.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FlightServiceException extends RuntimeException{
    public FlightServiceException(String message) {
        super(message);
    }
}
