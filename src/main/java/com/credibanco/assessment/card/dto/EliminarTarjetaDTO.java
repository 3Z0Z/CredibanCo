package com.credibanco.assessment.card.dto;

import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class EliminarTarjetaDTO {
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String pan;
    
    @NotNull
    @DecimalMin(value = "1", inclusive = true, message = "El número de validación debe ser mayor o igual a 1")
    @DecimalMax(value = "100", inclusive = true, message = "El número de validación debe ser menor o igual a 100")
    private Integer numValidacion;
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String panConfirm;
    
    private String codigo;
    
    private String mensaje;

    //RequestDTO
    public EliminarTarjetaDTO(String pan, int numValidacion, String panConfirm) {
        this.pan = pan;
        this.numValidacion = numValidacion;
        this.panConfirm = panConfirm;
    }

    //ResponseDTO
    public EliminarTarjetaDTO() { }

    public String getPanConfirm() {
        return panConfirm;
    }

    public void setPanConfirm(String panConfirm) {
        this.panConfirm = panConfirm;
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Integer getNumValidacion() {
        return numValidacion;
    }

    public void setNumValidacion(Integer numValidacion) {
        this.numValidacion = numValidacion;
    }
}
