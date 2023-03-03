package com.credibanco.assessment.card.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnrolarTarjetaDTO {
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String pan;
    
    @NotNull
    @DecimalMin(value = "1", inclusive = true, message = "El número de validación debe ser mayor o igual a 1")
    @DecimalMax(value = "100", inclusive = true, message = "El número de validación debe ser menor o igual a 100")
    private Integer numValidacion;
    
    private String codigo;
    
    private String mensaje;
    
    public EnrolarTarjetaDTO() { }

    //RequestDTO
    public EnrolarTarjetaDTO(String pan, int numValidacion) {
        this.pan = pan;
        this.numValidacion = numValidacion;
    }

    //ResponseDTO
    public EnrolarTarjetaDTO(String pan, String codigo, String mensaje) {
        this.pan = pan;
        this.codigo = codigo;
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
