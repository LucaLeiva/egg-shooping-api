package com.globant.training.eggshoopingv1.advice;

import com.globant.training.eggshoopingv1.exception.ClientAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.ClientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// si se lanza la excepcion, esta clase la manejara y devolvera un 404 con el mensaje que se encuentra en
// ClientNotFoundException
@ControllerAdvice
public class ClientAdvice {
    @ResponseBody
    @ExceptionHandler(ClientNotFoundException.class) // esta catchea la exception
    @ResponseStatus(HttpStatus.NOT_FOUND) // este indica que tipo de mensaje HTTP debe devolver
    String clientNotFoundHandler(ClientNotFoundException ex){
        return ex.getMessage(); // aca devuelve el mensaje personalizado de la excepcion
    }

    @ResponseBody
    @ExceptionHandler(ClientAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String clientAlreadyExistsHandler(ClientAlreadyExistsException ex){
        return ex.getMessage();
    }
}
