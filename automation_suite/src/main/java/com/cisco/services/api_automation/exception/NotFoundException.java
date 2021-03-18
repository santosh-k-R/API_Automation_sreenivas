package com.cisco.services.api_automation.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String e) {
        super(e);
    }

    public NotFoundException(String e, Throwable cause) {
        super(e, cause);
    }
}
