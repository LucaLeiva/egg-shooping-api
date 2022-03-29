package com.globant.training.eggshoopingv1.exception;

public class ProductAlreadyExistsException extends RuntimeException{
    public ProductAlreadyExistsException(Long id){
        super("----------Product " + id + " already existis----------\n");
    }

    public ProductAlreadyExistsException(String name){
        super("----------Product " + name + " already existis----------\n");
    }
}
