package com.globant.training.eggshoopingv1.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id){
        super("----------Could not find Order " + id + "----------\n");
    }
}
