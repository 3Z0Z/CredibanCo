package com.credibanco.assessment.card.service.impl;

import com.credibanco.assessment.card.dto.*;
import com.credibanco.assessment.card.exceptions.*;
import com.credibanco.assessment.card.model.*;
import com.credibanco.assessment.card.model.enums.*;
import com.credibanco.assessment.card.repository.*;
import com.credibanco.assessment.card.service.Servicio;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ServicioImpl implements Servicio{
    
    @Autowired
    private ClienteRepository clienteRepo;
    
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
        return (int) (Math.floor(Math.random()*(100-1+1))+1);
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
        Cliente cliente = this.clienteRepo.findByCedulaOrTelefono(request.getCedula(), request.getTelefono());
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        if(cliente != null || tarjeta != null){
            throw new RegistroExistenteException("Cliente o tarjeta ya existente en el sistema");
        }
        cliente = new Cliente(request.getNombre(), request.getApellidoPaterno(), request.getApellidoMaterno(), request.getCedula(), request.getTelefono());
        tarjeta = new Tarjeta(request.getPan(), genNumValidacion(), request.getTipo(), TarjetaEstadoEnum.CREADA, cliente);
        try{
            this.tarjetaRepo.save(tarjeta);
            return new CrearTarjetaDTO(enmascararPan(request.getPan()), tarjeta.getNumValidacion(), CodigoRespuesta.CERO, "Exito");
        } catch(Exception e){
            throw new NoGuardadoException("Error al procesar la informacion", e);
        }
    }

    @Override
    public EnrolarTarjetaDTO enrolarTarjeta(EnrolarTarjetaDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        if(tarjeta == null){
            return new EnrolarTarjetaDTO(enmascararPan(request.getPan()), CodigoRespuesta.UNO, "Tarjeta no existe");
        }
        if(request.getNumValidacion() != tarjeta.getNumValidacion()) {
            return new EnrolarTarjetaDTO(enmascararPan(tarjeta.getPan()), CodigoRespuesta.DOS, "Numero de validacion invalido");
        }
        try{
            tarjeta.setEstado(TarjetaEstadoEnum.ENROLADA);
            this.tarjetaRepo.save(tarjeta);
            return new EnrolarTarjetaDTO(enmascararPan(tarjeta.getPan()), CodigoRespuesta.CERO, "Exito");
        } catch(NoGuardadoException e){
            throw new NoGuardadoException("Error al procesar la informacion", e);
        }
    }

    @Override
    public ConsultarTarjetaDTO consultarTarjeta(ConsultarTarjetaDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        if(tarjeta == null){
            throw new NoEncontradoException("El numero PAN proporcionado no esta registrado");
        }
        Cliente cliente = tarjeta.getCliente();
        return new ConsultarTarjetaDTO(enmascararPan(tarjeta.getPan()), cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno(), cliente.getCedula(), cliente.getTelefono(), tarjeta.getEstado());
    }

    @Override
    public EliminarTarjetaDTO eliminarTarjeta(EliminarTarjetaDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        if (tarjeta == null) {
            return new EliminarTarjetaDTO(CodigoRespuesta.UNO, "La tarjeta no existe");
        }
        if(!request.getPan().equals(request.getPanConfirm()) && request.getNumValidacion() != tarjeta.getNumValidacion()){
            return new EliminarTarjetaDTO(CodigoRespuesta.UNO, "No se ha eliminado la tarjeta");
        }
        try {
            List<Transaccion> transacciones = this.transaccionRepo.findByIdTarjeta(tarjeta.getIdTarjeta());
            this.transaccionRepo.deleteAll(transacciones);
            this.tarjetaRepo.delete(tarjeta);
            return new EliminarTarjetaDTO(CodigoRespuesta.CERO, "Se ha eliminado la tarjeta");
        } catch (DataAccessException e){
            return new EliminarTarjetaDTO(CodigoRespuesta.UNO, "No se ha eliminado la tarjeta");
        }
    }

    @Override
    public CrearTransaccionDTO crearTransaccion(CrearTransaccionDTO request) {
        Tarjeta tarjeta = this.tarjetaRepo.findByPan(request.getPan());
        if(tarjeta == null){
            return new CrearTransaccionDTO(request.getNumReferencia(), TransaccionEstadoEnum.RECHAZADA,"Tarjeta no existe", CodigoRespuesta.UNO);
        }
        if(!tarjeta.getEstado().equals(TarjetaEstadoEnum.ENROLADA)){
            return new CrearTransaccionDTO(request.getNumReferencia(), TransaccionEstadoEnum.RECHAZADA, "Tarjeta no enrolada", CodigoRespuesta.DOS);
        }
        Transaccion tran = new Transaccion(request.getNumReferencia(), request.getTotalCompra(), request.getDireccionCompra(), TransaccionEstadoEnum.APROVADA, LocalDateTime.now(ZoneId.systemDefault()), tarjeta);
        try {
            this.transaccionRepo.save(tran);
            return new CrearTransaccionDTO(tran.getNumReferencia(), tran.getEstado(), "Compra exitosa", CodigoRespuesta.CERO);
        } catch(Exception e){
            throw new NoGuardadoException("Error al procesar la informacion", e);
        }
    }

    @Override
    public AnularTransaccionDTO anularTransaccion(AnularTransaccionDTO request) {
        Transaccion transaccion = this.transaccionRepo.findByNumReferenciaAndTotalCompraAndEstado(request.getNumReferencia(), request.getTotalCompra(), TransaccionEstadoEnum.APROVADA);
        if(transaccion == null){
            throw new NoEncontradoException("Transaccion no encontrada o ya esta anulada");
        }
        if(!request.getNumReferencia().equals(transaccion.getNumReferencia())){
            return new AnularTransaccionDTO(request.getNumReferencia(), CodigoRespuesta.UNO, "Numero de referencia invalido");
        }
        if(!obtenerTiempo(transaccion.getHoraCompra())){
            return new AnularTransaccionDTO(request.getNumReferencia(), CodigoRespuesta.DOS, "No se puede anular transaccion");
        }
        try{
            transaccion.setEstado(TransaccionEstadoEnum.ANULADA);
            this.transaccionRepo.save(transaccion);
            return new AnularTransaccionDTO(transaccion.getNumReferencia(), CodigoRespuesta.CERO, "Compra Anulada");
        } catch(NoGuardadoException e){
            throw new NoGuardadoException("Error al procesar la informacion", e);
        }
    }
}