package com.globant.training.eggshoopingv1.advice;

import com.globant.training.eggshoopingv1.exception.OrderLineAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.OrderLineNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderLineAdvice {
    @ResponseBody
    @ExceptionHandler(OrderLineNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderLineNotFoundHandler(OrderLineNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OrderLineAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String orderLineAlreadyExistsHandler(OrderLineAlreadyExistsException ex){
        return ex.getMessage();
    }
}
