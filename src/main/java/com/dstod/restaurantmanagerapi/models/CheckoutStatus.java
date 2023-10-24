package com.dstod.restaurantmanagerapi.models;

import java.util.List;

public record CheckoutStatus(Boolean successfulCheckout, String statusMessage, List<String> errors) {
}
