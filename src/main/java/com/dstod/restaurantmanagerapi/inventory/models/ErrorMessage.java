package com.dstod.restaurantmanagerapi.inventory.models;

public class ErrorMessage {
    private String field;
    private String message;

    @Override
    public String toString() {
        return "{\"field\":\"" + this.field + "\",\"message\":" + "\"" + this.message + "\"}";
    }
}
