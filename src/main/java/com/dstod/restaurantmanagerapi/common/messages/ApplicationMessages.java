package com.dstod.restaurantmanagerapi.common.messages;

public interface ApplicationMessages {
    String PRODUCT_WASTED = "Product with id %d (%s) is wasted";
    String PRODUCT_MISSING = "Product with %d (%s) is missing in the inventory!";
    String PRODUCT_OUT_OF_STOCK = "Product with id %d (%s) is out of stock";
    String RECIPE_NOT_FOUND = "Recipe with id %d, not found!";
    String PRODUCT_NOT_FOUND = "Product with id %d was not found!";
    String USERNAME_EXISTS = "Username %s is already exist!";
    String EMAIL_EXISTS = "Email %s is already exist!";
    String PHONE_NUMBER_EXISTS = "Phone number %s is already exist!";
    String USER_CREATED = "User created!";
    String USER_ID_NOT_EXISTS = "User with id %d does not exist!";
}
