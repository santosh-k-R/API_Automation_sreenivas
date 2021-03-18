package com.cisco.services.api_automation.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String e) {
        super(e);
    }

    public BadRequestException(String e, Throwable cause) {
        super(e, cause);
    }
}
