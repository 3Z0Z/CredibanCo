package com.credibanco.assessment.card.service;

import com.credibanco.assessment.card.dto.*;

public interface Servicio {
    
    public CrearTarjetaDTO crearTarjeta(CrearTarjetaDTO request);
    
    public EnrolarTarjetaDTO enrolarTarjeta(EnrolarTarjetaDTO request);
    
    public ConsultarTarjetaDTO consultarTarjeta(ConsultarTarjetaDTO request);
    
    public EliminarTarjetaDTO eliminarTarjeta(EliminarTarjetaDTO request);
    
    public CrearTransaccionDTO crearTransaccion(CrearTransaccionDTO request);
    
    public AnularTransaccionDTO anularTransaccion(AnularTransaccionDTO request);
}
