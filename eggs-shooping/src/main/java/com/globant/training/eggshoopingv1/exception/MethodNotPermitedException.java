package com.globant.training.eggshoopingv1.exception;

import com.globant.training.eggshoopingv1.util.Status;

public class MethodNotPermitedException extends RuntimeException{
    public MethodNotPermitedException(Status status){
        super("Method not allowed for this status " + status);
    }
}
