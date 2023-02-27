package com.credibanco.assessment.card.dto;

import com.credibanco.assessment.card.model.enums.TarjetaTipoEnum;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CrearTarjetaDTO {
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String pan;
    
    @NotBlank(message = "El campo de nombre es requerido")
    @Size(max = 255, message = "Ha superado el limite de caracteres permitidos")
    @Pattern(regexp = "^([A-Z]{1}[a-z]+[ ]*){1,2}$", message = "Revise que el formato cumpla con lo establecido(nombre capitalizado y sin espacios)")
    private String nombre;
    
    @NotBlank(message = "El campo de apellido paterno es requerido")
    @Size(max = 45, message = "Ha superado el limite de caracteres permitidos")
    @Pattern(regexp = "^[A-Z]{1}[a-z]+$", message = "Revise que el formato cumpla con lo establecido(nombre capitalizado y sin espacios)")
    private String apellidoPaterno;
    
    @NotBlank(message = "El campo de apellido materno es requerido")
    @Size(max = 45, message = "Ha superado el limite de caracteres permitidos")
    @Pattern(regexp = "^[A-Z]{1}[a-z]+$", message = "Revise que el formato cumpla con lo establecido(nombre capitalizado y sin espacios)")
    private String apellidoMaterno;
    
    @NotBlank(message = "El campo de cedula es requerido")
    @Size(min = 10, max = 15, message = "El campo de cedula debe de tener entre 10 y 15 digitos")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "El campo de cedula debe de tener solo numeros")
    private String cedula;
    
    @NotBlank(message = "El campo de telefono es requerido")
    @Size(min = 10, max = 10, message = "El campo de telefono debe de tener 10 digitos")
    @Pattern(regexp = "^[0-9]{10}$", message = "El campo de telefono debe de tener solo numeros")
    private String telefono;
    
    @NotNull
    private TarjetaTipoEnum tipo;
    
    private int numValidacion;
    
    private String codigo;
    
    private String mensaje;
    
    public CrearTarjetaDTO() { }
    
    //RequestDTO
    public CrearTarjetaDTO(String pan, String nombre, String apellidoPaterno, String apellidoMaterno, String cedula, String telefono, TarjetaTipoEnum tipo) {
        this.pan = pan;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.cedula = cedula;
        this.telefono = telefono;
        this.tipo = tipo;
    }

    //ResponseDTO
    public CrearTarjetaDTO(String pan, int numValidacion) {
        this.pan = pan;
        this.numValidacion = numValidacion;
    }
    
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public TarjetaTipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TarjetaTipoEnum tipo) {
        this.tipo = tipo;
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

    public int getNumValidacion() {
        return numValidacion;
    }

    public void setNumValidacion(int numValidacion) {
        this.numValidacion = numValidacion;
    }
}