package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.dto.*;
import com.credibanco.assessment.card.model.enums.CodigoRespuesta;
import com.credibanco.assessment.card.service.Servicio;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/credibanco")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class Controlador {
    
    @Autowired
    private Servicio servicio;
    
    @PostMapping("/nueva")
    @ResponseBody
    public CrearTarjetaDTO crearTarjeta(@RequestBody @Valid CrearTarjetaDTO request){
        CrearTarjetaDTO response = this.servicio.crearTarjeta(request);
        if(response.getCodigo().equals(CodigoRespuesta.UNO)){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la inserción de la tarjeta");
        }
        return response;
    }
    
    @PutMapping("/enrolar")
    @ResponseBody
    public EnrolarTarjetaDTO enrolarTarjeta(@RequestBody @Valid EnrolarTarjetaDTO request){
        EnrolarTarjetaDTO response = this.servicio.enrolarTarjeta(request);
        return response;
    }
    
    @GetMapping("/consulta/{pan}")
    @ResponseBody
    public ConsultarTarjetaDTO consultarTarjeta(@PathVariable("pan") String pan){
        ConsultarTarjetaDTO request = new ConsultarTarjetaDTO();
        request.setPan(pan);
        ConsultarTarjetaDTO response = this.servicio.consultarTarjeta(request);
        return response;
    }
    
    @DeleteMapping("/eliminar?{pan}&{numValidacion}&panConfirm")
    @ResponseBody
    public EliminarTarjetaDTO eliminarTarjeta(@PathVariable("pan") String pan, @PathVariable("numValidacion") int nunValidacion, @PathVariable("panConfirm") String panConfirm){
        EliminarTarjetaDTO request = new EliminarTarjetaDTO();
        request.setPan(pan);
        request.setNumValidacion(nunValidacion);
        request.setPanConfirm(panConfirm);
        EliminarTarjetaDTO response = this.servicio.eliminarTarjeta(request);
        return response;
    }
    
    @PostMapping("/transaccion")
    @ResponseBody
    public CrearTransaccionDTO crearTransaccion(@RequestBody @Valid CrearTransaccionDTO request){
        CrearTransaccionDTO response = this.servicio.crearTransaccion(request);
        return response;
    }
    
    @PutMapping("/anular")
    @ResponseBody
    public AnularTransaccionDTO anularTransaccion(@RequestBody @Valid AnularTransaccionDTO request){
        AnularTransaccionDTO response = this.servicio.anularTransaccion(request);
        return response;
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}