package com.globant.training.eggshoopingv1.exception;

public class IncorrectParameterException extends RuntimeException{
    public IncorrectParameterException(){
        super("----------You introduced a wrong parameter in the URL request----------\n");
    }
}
