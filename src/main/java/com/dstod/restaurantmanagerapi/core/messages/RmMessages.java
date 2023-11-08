package com.dstod.restaurantmanagerapi.core.messages;

public interface RmMessages {
    String PRODUCT_WASTED = "Product with id %d (%s) is wasted";
    String PRODUCT_MISSING = "Product with %d (%s) is missing in the inventory!";
    String PRODUCT_OUT_OF_STOCK = "Product with id %d (%s) is out of stock";
    String RECIPE_NOT_FOUND = "Recipe with id %d, not found!";
}
