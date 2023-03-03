package com.credibanco.assessment.card.exceptions;

public class NoGuardadoException extends RuntimeException{
    public NoGuardadoException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
