package com.globant.training.eggshoopingv1.exception;

public class InsufficientStockException extends RuntimeException{
    public InsufficientStockException(String product){
        super(String.format("Not enough stock of %s", product));
    }
}
