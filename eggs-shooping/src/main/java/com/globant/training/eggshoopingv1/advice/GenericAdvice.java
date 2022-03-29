package com.globant.training.eggshoopingv1.advice;

import com.globant.training.eggshoopingv1.exception.IncorrectParameterException;
import com.globant.training.eggshoopingv1.exception.MethodNotPermitedException;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// avisos genericos para todas las clases
@ControllerAdvice
public class GenericAdvice {
    @ResponseBody
    @ExceptionHandler(MethodNotPermitedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String methodNotFoundHandler(MethodNotPermitedException ex){
        return ex.getMessage();
    }

    // devuelvo Bad Request en vez de Method Not Allowed porque creo que no es correcto y tampoco hay otro que
    // corresponda, creo yo
    @ResponseBody
    @ExceptionHandler(IncorrectParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String incorrectParameterHandler(IncorrectParameterException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String propertyValueHandler(PropertyValueException ex){
        return "A required field is empty";
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String illegalArgumentHandler(IllegalArgumentException ex){
        return "The given id must not be null";
    }
}
