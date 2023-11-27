package com.dstod.restaurantmanagerapi.common.messages;

public interface ApplicationMessages {
    String PRODUCT_WASTED = "Product with id %d (%s) is wasted";
    String PRODUCT_WRONG_CATEGORY = "Product category %s is not valid!";
    String PRODUCT_WRONG_UNITS = "Product unit %s is not valid!";
    String PRODUCT_SUCCESSFULLY_ADDED = "Successfully added product!";
    String INVENTORY_PRODUCT_MISSING = "Product with %d (%s) is missing in the inventory";
    String PRODUCT_OUT_OF_STOCK = "Product with id %d (%s) is out of stock";
    String RECIPE_NOT_FOUND = "Recipe with id %d, not found";
    String PRODUCT_NOT_FOUND = "Product with id %d was not found";
    String DUPLICATED_PRODUCT = "Product with name %s is already in the inventory";
    String USERNAME_EXISTS = "Username %s already exist";
    String EMAIL_EXISTS = "Email %s is already exist";
    String PHONE_NUMBER_EXISTS = "Phone number %s already exist";
    String USER_CREATED = "User created!";
    String USER_ID_NOT_EXISTS = "User with id %d does not exist";
    String SUPPLIER_NAME_EXISTS = "Supplier with name %s already exist";
    String SUPPLIER_EMAIL_EXISTS = "Supplier with email %s already exist";
    String SUPPLIER_PHONE_NUMBER_EXISTS = "Supplier with phone number %s already exist";
    String SUPPLIER_NOT_FOUND = "Supplier with id %d was not found";
}
