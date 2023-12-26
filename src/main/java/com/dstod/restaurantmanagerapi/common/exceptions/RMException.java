package com.dstod.restaurantmanagerapi.common.exceptions;

import org.springframework.http.HttpStatus;

public class RMException extends RuntimeException{
    private String message;
    private String globalMessage;
    private HttpStatus status;

    public RMException(String errorMessage, String globalMessage, HttpStatus status) {
        this.message = errorMessage;
        this.globalMessage = globalMessage;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public RMException setMessage(String message) {
        this.message = message;
        return this;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public RMException setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getGlobalMessage() {
        return globalMessage;
    }

    public RMException setGlobalMessage(String globalMessage) {
        this.globalMessage = globalMessage;
        return this;
    }
}
