package org.com.exception;

public class ValidationException extends RuntimeException {
    public String field;
    
    public ValidationException(String field, String message) {
        super(message);
        this.field = field;
    }
}