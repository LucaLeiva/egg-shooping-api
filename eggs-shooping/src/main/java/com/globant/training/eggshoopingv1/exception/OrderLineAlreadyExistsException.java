package com.globant.training.eggshoopingv1.exception;

public class OrderLineAlreadyExistsException extends RuntimeException{
    public OrderLineAlreadyExistsException(Long id){
        super("----------OrderLine " + id + " already existis----------\n");
    }
}
