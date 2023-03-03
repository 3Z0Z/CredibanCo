package com.credibanco.assessment.card.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnularTransaccionDTO {
    
    @NotBlank(message = "El numero pan es requerido")
    @Size(min = 16, max = 19, message = "El numero pan debe de ser entre 16 a 19 digitos")
    @Pattern(regexp = "^[0-9]{16,19}$", message = "El numero pan debe contener solo numeros")
    private String pan;
    
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
    
    public AnularTransaccionDTO() { }

    //RequestDTO
    public AnularTransaccionDTO(String pan, String numReferencia, BigDecimal totalCompra) {
        this.pan = pan;
        this.numReferencia = numReferencia;
        this.totalCompra = totalCompra;
    }

    //ResponseDTO
    public AnularTransaccionDTO(String numReferencia, String codigo, String mensaje) {
        this.numReferencia = numReferencia;
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
}