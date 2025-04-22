package com.cognizant.healthcare.exception;

import java.io.Serializable;

public class OperationFailedException extends RuntimeException implements Serializable {
	 private static final long serialVersionUID = 1L;
    public OperationFailedException(String message)    {
        super(message);
    }
}

