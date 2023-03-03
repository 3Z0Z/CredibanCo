package com.credibanco.assessment.card.controller;

import com.credibanco.assessment.card.dto.*;
import com.credibanco.assessment.card.exceptions.ErrorMessage;
import com.credibanco.assessment.card.exceptions.NoGuardadoException;
import com.credibanco.assessment.card.exceptions.RegistroExistenteException;
import com.credibanco.assessment.card.exceptions.NoEncontradoException;
import com.credibanco.assessment.card.model.enums.CodigoRespuesta;
import com.credibanco.assessment.card.service.Servicio;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credibanco")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class Controlador {
    
    @Autowired
    private Servicio servicio;
    
    @PostMapping("/nueva")
    @ResponseBody
    public ResponseEntity<Object> crearTarjeta(@RequestBody @Valid CrearTarjetaDTO request){
        try{
            CrearTarjetaDTO response = this.servicio.crearTarjeta(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch(NoGuardadoException e) {
            ErrorMessage error = new ErrorMessage(CodigoRespuesta.UNO, "Fallido");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch(RegistroExistenteException e){
            ErrorMessage error = new ErrorMessage(CodigoRespuesta.UNO, "Cliente ya existente en el sistema");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }
    
    @PutMapping("/enrolar")
    @ResponseBody
    public ResponseEntity<Object> enrolarTarjeta(@RequestBody @Valid EnrolarTarjetaDTO request){
        try{
            EnrolarTarjetaDTO response = this.servicio.enrolarTarjeta(request);
            return switch (response.getCodigo()){
                case CodigoRespuesta.UNO -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
                case CodigoRespuesta.DOS -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                default -> ResponseEntity.ok().body(response);
            };
        } catch(NoGuardadoException e){
            ErrorMessage error = new ErrorMessage(CodigoRespuesta.UNO, "Fallido");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/consulta/{pan}")
    @ResponseBody
    public ResponseEntity<Object> consultarTarjeta(@PathVariable("pan") String pan){
        ConsultarTarjetaDTO request = new ConsultarTarjetaDTO(pan);
        try{
            ConsultarTarjetaDTO response = this.servicio.consultarTarjeta(request);
            return ResponseEntity.ok().body(response);
        } catch(NoEncontradoException e){
            ConsultarTarjetaDTO response = new ConsultarTarjetaDTO("El numero PAN proporcionado no esta registrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    
    @DeleteMapping("/eliminar/{pan}/{numValidacion}/{panConfirm}")
    @ResponseBody
    public ResponseEntity<Object> eliminarTarjeta(@PathVariable("pan") String pan, @PathVariable("numValidacion") int nunValidacion, @PathVariable("panConfirm") String panConfirm){
        EliminarTarjetaDTO request = new EliminarTarjetaDTO(pan, nunValidacion, panConfirm);
        try{
            EliminarTarjetaDTO response = this.servicio.eliminarTarjeta(request);
            return switch (response.getCodigo()){
                case CodigoRespuesta.UNO -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                default -> ResponseEntity.ok(response);
            };
        } catch(DataAccessException e){
            ErrorMessage error = new ErrorMessage("Error al intentar eliminar");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PostMapping("/transaccion")
    @ResponseBody
    public ResponseEntity<Object> crearTransaccion(@RequestBody @Valid CrearTransaccionDTO request){
        try{
            CrearTransaccionDTO response = this.servicio.crearTransaccion(request);
            return switch (response.getCodigo()){
                case CodigoRespuesta.UNO -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                case CodigoRespuesta.DOS -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                default -> ResponseEntity.ok(response);
            };
        } catch(NoGuardadoException e){
            ErrorMessage error = new ErrorMessage("No se ha procesado la solicitud correctamente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @PutMapping("/anular")
    @ResponseBody
    public ResponseEntity<Object> anularTransaccion(@RequestBody @Valid AnularTransaccionDTO request){
        try {
            AnularTransaccionDTO response = this.servicio.anularTransaccion(request);
            return switch (response.getCodigo()){
                case CodigoRespuesta.UNO -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
                case CodigoRespuesta.DOS -> ResponseEntity.status(HttpStatus.CONFLICT).body(response);
                default -> ResponseEntity.ok().body(response);
            };
        } catch(NoEncontradoException e) {
            ErrorMessage error = new ErrorMessage("Transaccion no encontrada o ya esta anulada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch(NoGuardadoException e){
            ErrorMessage error = new ErrorMessage("No se ha procesado la solicitud correctamente");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
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