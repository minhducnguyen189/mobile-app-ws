package com.appdeveloperblog.app.ws.exceptions;

public class UserServiceException extends RuntimeException {


    private static final long serialVersionUID = 3606970802060912990L;

    public UserServiceException(String message) {
        super(message);
    }
}
