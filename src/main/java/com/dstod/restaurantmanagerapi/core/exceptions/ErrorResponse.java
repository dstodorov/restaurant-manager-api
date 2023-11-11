package com.dstod.restaurantmanagerapi.core.exceptions;

public class ErrorResponse {
    private final String module;
    private final String exceptionMessage;

    public ErrorResponse(String module, String exceptionMessage) {
        this.module = module;
        this.exceptionMessage = exceptionMessage;
    }

    public String getModule() {
        return module;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
