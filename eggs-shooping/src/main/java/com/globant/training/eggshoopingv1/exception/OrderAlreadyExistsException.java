package com.globant.training.eggshoopingv1.exception;

public class OrderAlreadyExistsException extends RuntimeException{
    public OrderAlreadyExistsException(Long id){
        super("----------Order " + id + " already existis----------\n");
    }
}
