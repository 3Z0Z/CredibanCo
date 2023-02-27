package com.credibanco.assessment.card.model;

import com.credibanco.assessment.card.model.enums.TarjetaEstadoEnum;
import com.credibanco.assessment.card.model.enums.TarjetaTipoEnum;
import jakarta.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;

@Entity
@Table(name = "tarjeta")
public class Tarjeta implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private long idTarjeta;
    
    @Column(name = "pan", nullable = false)
    private String pan;
    
    @Column(name = "num_validacion", nullable = false)
    private int numValidacion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TarjetaTipoEnum tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private TarjetaEstadoEnum estado;
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    public Tarjeta(String pan, int numValidacion, TarjetaTipoEnum tipo, TarjetaEstadoEnum estado, Cliente cliente) {
        this.pan = pan;
        this.numValidacion = numValidacion;
        this.tipo = tipo;
        this.estado = estado;
        this.cliente = cliente;
    }

    public Tarjeta() { }

    public Tarjeta(String pan, int numValidacion, TarjetaTipoEnum tipo, TarjetaEstadoEnum estado) {
        this.pan = pan;
        this.numValidacion = numValidacion;
        this.tipo = tipo;
        this.estado = estado;
    }

    public Tarjeta(String pan, int numValidacion) {
        this.pan = pan;
        this.numValidacion = numValidacion;
    }

    public Tarjeta(String pan, TarjetaEstadoEnum estado) {
        this.pan = pan;
        this.estado = estado;
    }

    public Tarjeta(String pan) {
        this.pan = pan;
    }
    
    public Tarjeta(int numValidacion){
        this.numValidacion = numValidacion;
    }

    public long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(@Valid String pan) {
        this.pan = pan;
    }

    public int getNumValidacion() {
        return numValidacion;
    }

    public void setNumValidacion(int numValidacion) {
        this.numValidacion = numValidacion;
    }

    public TarjetaTipoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TarjetaTipoEnum tipo) {
        this.tipo = tipo;
    }

    public TarjetaEstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(TarjetaEstadoEnum estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
