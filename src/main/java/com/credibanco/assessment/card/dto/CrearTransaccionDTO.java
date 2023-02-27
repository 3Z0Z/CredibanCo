package com.credibanco.assessment.card.dto;

import com.credibanco.assessment.card.model.enums.TransaccionEstadoEnum;
import java.math.BigDecimal;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CrearTransaccionDTO {
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String pan;
    
    @NotBlank(message = "La direccion de compra es requerida")
    @Size(max = 255, message = "Ha superado el limite de caracteres permitidos")
    private String direccionCompra;
    
    @NotNull(message = "El total de la compra es requerido")
    @DecimalMin(value = "0.01", inclusive = true, message = "El total de la compra debe ser mayor o igual a 0.01")
    @Digits(integer = 10, fraction = 2, message = "El total de la compra debe tener como máximo 10 dígitos enteros y 2 decimales")
    protected BigDecimal totalCompra;
    
    @NotBlank(message = "El numero de referencia es requerido")
    @Size(min = 6, max = 6, message = "El numero de referencia debe contener 6 digitos")
    @Pattern(regexp = "^[0-9]{6}$", message = "El numero de referencia debe de contener solo numeros")
    private String numReferencia;
    
    private String codigo;
    
    private String mensaje;
    
    private TransaccionEstadoEnum estado;
    
    private LocalDateTime horaCompra;

    //RequestDTO
    public CrearTransaccionDTO(String pan, String numReferencia, BigDecimal totalCompra, String direccionCompra) {
        this.pan = pan;
        this.numReferencia = numReferencia;
        this.totalCompra = totalCompra;
        this.direccionCompra = direccionCompra;
    }

    //ResponseDTO
    public CrearTransaccionDTO(String numReferencia) {
        this.numReferencia = numReferencia;
    }

    public String getDireccionCompra() {
        return direccionCompra;
    }

    public void setDireccionCompra(String direccionCompra) {
        this.direccionCompra = direccionCompra;
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

    public TransaccionEstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(TransaccionEstadoEnum estado) {
        this.estado = estado;
    }
    
    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }
    
    public String getNumReferencia() {
        return numReferencia;
    }

    public void setNumReferencia(String numReferencia) {
        this.numReferencia = numReferencia;
    }
    
    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public LocalDateTime getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(LocalDateTime horaCompra) {
        this.horaCompra = horaCompra;
    }
}
