package com.credibanco.assessment.card.model;

import com.credibanco.assessment.card.model.enums.TransaccionEstadoEnum;
import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaccion")
public class Transaccion implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private long idTransaccion;
    
    @Column(name = "num_referencia", nullable = false)
    private String numReferencia;
    
    @Column(name = "total_compra", nullable = false)
    private BigDecimal totalCompra;
    
    @Column(name = "direccion_compra", nullable = false)
    private String direccionCompra;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private TransaccionEstadoEnum estado;
    
    @NotNull(message = "La fecha de creación es requerida")
    @Column(name = "hora_compra", columnDefinition = "TIMESTAMP")
    private LocalDateTime horaCompra;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_tarjeta")
    private Tarjeta tarjeta;

    public Transaccion() { }

    public Transaccion(String numReferencia, BigDecimal totalCompra, String direccionCompra, TransaccionEstadoEnum estado, LocalDateTime horaCompra, Tarjeta tarjeta) {
        this.numReferencia = numReferencia;
        this.totalCompra = totalCompra;
        this.direccionCompra = direccionCompra;
        this.estado = estado;
        this.horaCompra = horaCompra;
        this.tarjeta = tarjeta;
    }

    public Transaccion(String numReferencia, BigDecimal totalCompra) {
        this.numReferencia = numReferencia;
        this.totalCompra = totalCompra;
    }

    public long getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(long idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNumReferencia() {
        return numReferencia;
    }

    public void setNumReferencia(String numReferencia) {
        this.numReferencia = numReferencia;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

    public String getDireccionCompra() {
        return direccionCompra;
    }

    public void setDireccionCompra(String direccionCompra) {
        this.direccionCompra = direccionCompra;
    }

    public TransaccionEstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(TransaccionEstadoEnum estado) {
        this.estado = estado;
    }

    public LocalDateTime getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(LocalDateTime horaCompra) {
        this.horaCompra = horaCompra;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }
}