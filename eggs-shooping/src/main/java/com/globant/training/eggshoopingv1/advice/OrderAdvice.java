package com.globant.training.eggshoopingv1.advice;

import com.globant.training.eggshoopingv1.exception.OrderAlreadyExistsException;
import com.globant.training.eggshoopingv1.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderAdvice {
    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderNotFoundHandler(OrderNotFoundException ex){
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(OrderAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    String orderAlreadyExistsHandler(OrderAlreadyExistsException ex){
        return ex.getMessage();
    }
}
