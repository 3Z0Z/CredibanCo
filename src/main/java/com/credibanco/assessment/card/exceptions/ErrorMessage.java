package com.credibanco.assessment.card.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage implements Serializable {
    private String codigo;
    private String mensaje;
    
    public ErrorMessage(String codigo, String mensaje){
        this.codigo = codigo;
        this.mensaje = mensaje;
    }
    
    public ErrorMessage(String mensaje){
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
