package com.credibanco.assessment.card.dto;

import com.credibanco.assessment.card.model.enums.TarjetaEstadoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultarTarjetaDTO {
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String pan;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String cedula;
    private String telefono;
    private TarjetaEstadoEnum estado;

    public ConsultarTarjetaDTO() { }
    
    //RequestDTO
    public ConsultarTarjetaDTO(String pan) {
        this.pan = pan;
    }

    //ResponseDTO Ok
    public ConsultarTarjetaDTO(String pan, String nombre, String apellidoPaterno, String apellidoMaterno, String cedula, String telefono, TarjetaEstadoEnum estado) {
        this.pan = pan;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.cedula = cedula;
        this.telefono = telefono;
        this.estado = estado;
    }
    
    public TarjetaEstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(TarjetaEstadoEnum estado) {
        this.estado = estado;
    }

    public String getPan() {
        return pan;
    }
    
    @Valid
    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
