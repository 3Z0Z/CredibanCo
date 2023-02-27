package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.dto.*;
import com.credibanco.assessment.card.model.*;
import com.credibanco.assessment.card.model.enums.*;
import com.credibanco.assessment.card.repository.*;
import com.credibanco.assessment.card.service.Servicio;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ServicioImpl implements Servicio{
    
    @Autowired
    private TarjetaRepository tarjetaRepo;
    
    @Autowired
    private TransaccionRepository transaccionRepo;
    
    
    private static String enmascararPan(String pan){
       String num = "(\\d{6})(\\d+)(\\d{4})";
       String enmascarar = "$1****$3";
       return pan.replaceAll(num, enmascarar);
    }
    
    private static int genNumValidacion(){
        int numValidacion;
        return numValidacion = (int) (Math.floor(Math.random()*(100-1+1))+1);
    }
    
    private static boolean obtenerTiempo(LocalDateTime tiempoCompra){
        LocalDateTime actual = LocalDateTime.now();
        Duration duracion = Duration.between(actual, tiempoCompra);
        long minutes = duracion.toMinutes();
        //Devuelve verdadero si minutes <= a 5
        return minutes <= 5;
    }

    @Override
    public CrearTarjetaDTO crearTarjeta(CrearTarjetaDTO request) {
        Cliente cliente = new Cliente(request.getNombre(), request.getApellidoPaterno(), request.getApellidoMaterno(), request.getCedula(), request.getTelefono());
        Tarjeta tarjeta = new Tarjeta(request.getPan(), genNumValidacion(), request.getTipo(), TarjetaEstadoEnum.CREADA, cliente);
        Tarjeta tarGuardada = this.tarjetaRepo.save(tarjeta);
        CrearTarjetaDTO response = new CrearTarjetaDTO(enmascararPan(request.getPan()), tarjeta.getNumValidacion());
        if(tarGuardada != null){
            response.setMensaje("Exito");
            response.setCodigo(CodigoRespuesta.CERO);
        } else {
            response.setMensaje("Fallo");
            response.setCodigo(CodigoRespuesta.UNO);
        }
        return response;
    }

    @Override
    public EnrolarTarjetaDTO enrolarTarjeta(EnrolarTarjetaDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        EnrolarTarjetaDTO response = new EnrolarTarjetaDTO(enmascararPan(tarjeta.getPan()));
        if(tarjeta.getPan() == null){
            response.setCodigo(CodigoRespuesta.UNO);
            response.setMensaje("Tarjeta no existe");
        } else if(request.getNumValidacion() != tarjeta.getNumValidacion()) {
            response.setCodigo(CodigoRespuesta.DOS);
            response.setMensaje("Numero de validacion invalido");
        } else {
            tarjeta.setEstado(TarjetaEstadoEnum.ENROLADA);
            this.tarjetaRepo.save(tarjeta);
            response.setCodigo(CodigoRespuesta.CERO);
            response.setMensaje("Exito");
        }
        return response;
    }

    @Override
    public ConsultarTarjetaDTO consultarTarjeta(ConsultarTarjetaDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        Cliente cliente = tarjeta.getCliente();
        ConsultarTarjetaDTO response = new ConsultarTarjetaDTO(enmascararPan(tarjeta.getPan()), cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno(), cliente.getCedula(), cliente.getTelefono(), tarjeta.getEstado());
        return response;
    }

    @Override
    public EliminarTarjetaDTO eliminarTarjeta(EliminarTarjetaDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        EliminarTarjetaDTO response = new EliminarTarjetaDTO();
        if (tarjeta != null) {
            if(request.getPan().equals(request.getPanConfirm()) && request.getNumValidacion() == tarjeta.getNumValidacion()){
                try {
                    List<Transaccion> transacciones = this.transaccionRepo.findByIdTarjeta(tarjeta.getIdTarjeta());
                    this.transaccionRepo.deleteAll(transacciones);

                    this.tarjetaRepo.delete(tarjeta);
                    response.setCodigo(CodigoRespuesta.CERO);
                    response.setMensaje("Se ha eliminado la tarjeta");
                } catch (DataAccessException e){
                    response.setCodigo(CodigoRespuesta.UNO);
                    response.setMensaje("No se ha eliminar la tarjeta");
                }
            } else {
                response.setCodigo(CodigoRespuesta.UNO);
                response.setMensaje("No se ha eliminar la tarjeta");
            }
        } else {
            response.setCodigo(CodigoRespuesta.UNO);
            response.setMensaje("No se ha eliminar la tarjeta");
        }
        return response;
    }

    @Override
    public CrearTransaccionDTO crearTransaccion(CrearTransaccionDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        CrearTransaccionDTO response = new CrearTransaccionDTO(request.getNumReferencia());
        if(tarjeta != null){
            Transaccion transaccion = new Transaccion(request.getNumReferencia(), request.getTotalCompra(), request.getDireccionCompra(), TransaccionEstadoEnum.RECHAZADA, LocalDateTime.now(), tarjeta);
            if(tarjeta.getEstado().equals(TarjetaEstadoEnum.ENROLADA)){
                transaccion.setEstado(TransaccionEstadoEnum.APROVADA);
                Transaccion tranGuardada = this.transaccionRepo.save(transaccion);
                if(tranGuardada != null){
                    response.setCodigo(CodigoRespuesta.CERO);
                    response.setMensaje("Compra exitosa");
                    response.setEstado(tranGuardada.getEstado());
                }
            } else {
                response.setCodigo(CodigoRespuesta.DOS);
                response.setMensaje("Tarjeta no enrolada");
                response.setEstado(transaccion.getEstado());
            }    
        } else {
            response.setCodigo(CodigoRespuesta.UNO);
            response.setMensaje("Tarjeta no existe");
            response.setEstado(TransaccionEstadoEnum.RECHAZADA);
        }        
        return response;
    }

    @Override
    public AnularTransaccionDTO anularTransaccion(AnularTransaccionDTO request) {
        Transaccion transaccion = this.transaccionRepo.findByNumReferenciaAndTotalCompra(request.getNumReferencia(), request.getTotalCompra());
        Tarjeta tarjeta = transaccion.getTarjeta();
        AnularTransaccionDTO response = new AnularTransaccionDTO(request.getNumReferencia());
        if(tarjeta != null){
            if(request.getNumReferencia().equals(transaccion.getNumReferencia())){
                if(obtenerTiempo(transaccion.getHoraCompra())){
                    transaccion.setEstado(TransaccionEstadoEnum.ANULADA);
                    this.transaccionRepo.save(transaccion);
                    response.setCodigo(CodigoRespuesta.CERO);
                    response.setMensaje("Compra Anulada");
                } else {
                    response.setCodigo(CodigoRespuesta.DOS);
                    response.setMensaje("No se puede anular transaccion");
                }
            } else {
                response.setCodigo(CodigoRespuesta.UNO);
                response.setMensaje("Numero de referencia invalido");
            }
        } else {
            response.setCodigo(CodigoRespuesta.DOS);
            response.setMensaje("No se puede anular transaccion");
        }
        return response;
    }
}