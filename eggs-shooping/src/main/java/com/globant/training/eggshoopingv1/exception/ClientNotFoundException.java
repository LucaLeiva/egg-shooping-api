package com.globant.training.eggshoopingv1.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(Long id){
        super("----------Could not find Client " + id + "----------\n");
    }

    public ClientNotFoundException(String username){
        super("----------Could not find Client " + username + "----------\n");
    }
}
