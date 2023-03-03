package com.credibanco.assessment.card.exceptions;

public class RegistroExistenteException extends RuntimeException{
    public RegistroExistenteException(String mensaje) {
        super(mensaje);
    }
}
