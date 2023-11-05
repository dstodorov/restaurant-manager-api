package com.dstod.restaurantmanagerapi.inventory.models;

import org.springframework.http.HttpStatusCode;

import java.util.List;

public record CheckoutStatus(Boolean successfulCheckout, HttpStatusCode statusMessage, List<String> errors) {
}
