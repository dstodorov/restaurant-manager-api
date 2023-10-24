package com.dstod.restaurantmanagerapi.inventory.models;

import java.util.List;

public record CheckoutStatus(Boolean successfulCheckout, String statusMessage, List<String> errors) {
}
