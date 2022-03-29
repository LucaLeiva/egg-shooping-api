package com.globant.training.eggshoopingv1.exception;

public class OrderLineNotFoundException extends RuntimeException{
    public OrderLineNotFoundException(Long id){
        super("----------Could not find OrderLine " + id + "----------\n");
    }
}
