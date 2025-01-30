package com.paulocavalcante.refreshtoken.dtos.exceptions;

public class UnauthorizedException extends  RuntimeException{

    public UnauthorizedException(String message) {
        super(message);
    }
}
