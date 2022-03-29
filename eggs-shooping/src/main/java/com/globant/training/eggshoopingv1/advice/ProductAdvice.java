package com.globant.training.eggshoopingv1.advice;

import com.globant.training.eggshoopingv1.exception.InsufficientStockException;
import com.globant.training.eggshoopingv1.exception.ProductAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductAdvice {
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String clientNotFoundHandler(ProductNotFoundException ex){
        return ex.getMessage(); // aca devuelve el mensaje personalizado de la excepcion
    }

    @ResponseBody
    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String clientAlreadyExistsHandler(ProductAlreadyExistsException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String insufficientStockHandler(InsufficientStockException ex){
        return ex.getMessage();
    }
}
