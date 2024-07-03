package com.pavanelli.payments.errors;

public class InvalidFieldException extends RuntimeException {

    private final String field;
    private final String error;

    public InvalidFieldException(String field, String error) {
        super(error);
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}