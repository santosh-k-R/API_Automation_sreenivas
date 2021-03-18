package com.cisco.services.api_automation.exception;

public class NotAllowedException extends RuntimeException{
    public NotAllowedException(String e) {
        super(e);
    }

    public NotAllowedException(String e, Throwable cause) {
        super(e, cause);
    }
}
