package com.dstod.restaurantmanagerapi.inventory.models;

public class ErrorMessage {
    private String field;
    private String message;

    public ErrorMessage(String field, String message) {
        this.field = field;
        this.message = message;
    }

    @Override
    public String toString() {
        return "{\"field\":\"" + this.field + "\",\"message\":" + "\"" + this.message + "\"}";
    }
}
