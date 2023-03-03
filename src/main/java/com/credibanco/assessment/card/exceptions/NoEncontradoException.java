package com.credibanco.assessment.card.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoEncontradoException extends RuntimeException{
    
    public NoEncontradoException(String message) {
        super(message);
    }

    public NoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }      
}