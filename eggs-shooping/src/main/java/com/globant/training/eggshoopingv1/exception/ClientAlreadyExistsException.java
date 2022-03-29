package com.globant.training.eggshoopingv1.exception;

public class ClientAlreadyExistsException extends RuntimeException{
    public ClientAlreadyExistsException(Long id){
        super("----------Client " + id + " already existis----------\n");
    }

    public ClientAlreadyExistsException(String username){
        super("----------Client " + username + " already existis----------\n");
    }
}
