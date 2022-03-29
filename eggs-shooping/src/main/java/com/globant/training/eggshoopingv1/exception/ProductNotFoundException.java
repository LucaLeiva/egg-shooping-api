package com.globant.training.eggshoopingv1.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("----------Could not find Product " + id + "----------\n");
    }
}
