package com.dstod.restaurantmanagerapi.common.messages;

public interface ApplicationMessages {
    String PRODUCT_WASTED = "Product with id %d (%s) is wasted";
    String PRODUCT_WRONG_CATEGORY = "Product category %s is not valid";
    String PRODUCT_WRONG_UNITS = "Product unit %s is not valid";
    String PRODUCT_SUCCESSFULLY_CREATED = "Successfully created product";
    String PRODUCT_SUCCESSFULLY_UPDATED = "Successfully updated product";
    String INVENTORY_PRODUCT_MISSING = "Product with %d is missing in the inventory";
    String INVENTORY_PRODUCT_CREATED = "Inventory product was successfully created";
    String PRODUCT_OUT_OF_STOCK = "Product with id %d (%s) is out of stock";
    String RECIPE_NOT_FOUND = "Recipe with id %d, not found";
    String DUPLICATED_RECIPE = "Recipe with name %s already exist in the repository";
    String PRODUCT_NOT_FOUND = "Product with id %d was not found";
    String PRODUCTS_NOT_FOUND = "Missing products";
    String DUPLICATED_PRODUCT = "Product with name %s already exist in the inventory";
    String USERNAME_EXISTS = "Username %s already exist";
    String EMAIL_EXISTS = "Email %s is already exist";
    String PHONE_NUMBER_EXISTS = "Phone number %s already exist";
    String USER_CREATED = "User created!";
    String USER_ID_NOT_EXISTS = "User with id %d does not exist";
    String SUPPLIER_NAME_EXISTS = "Supplier with name %s already exist";
    String SUPPLIER_EMAIL_EXISTS = "Supplier with email %s already exist";
    String SUPPLIER_PHONE_NUMBER_EXISTS = "Supplier with phone number %s already exist";
    String SUPPLIER_NOT_FOUND = "Supplier with id %d was not found";
    String SUPPLIER_SUCCESSFULLY_CREATED = "Successfully created supplier";
    String SUPPLIER_SUCCESSFULLY_UPDATED = "Successfully updated supplier";
    String MISMATCHED_OBJECT_EXCEPTION = "Object id %d does not match object details in the update request";
    String RECIPE_SUCCESSFULLY_CREATED = "Successfully created recipe";
    String RECIPE_WRONG_CATEGORY = "Recipe category %s is not valid";
    String RECIPE_SUCCESSFULLY_UPDATED = "Successfully updated recipe";
    String RECIPE_PRODUCT_DUPLICATION = "Product with id %d is duplicated in the input request";
    String RECIPE_PRODUCT_SUCCESSFULLY_UPDATED = "Successfully added products to the recipe";
    String REFRESH_TOKEN_FAILURE = "Token refresh failed";
    String INVENTORY_ISSUES = "There are inventory stock issues";
    String SUCCESSFUL_CHECKOUT = "Successful products checkout";
    String USER_SUCCESSFULLY_UPDATED = "User %s was successfully updated";
    String TABLE_SUCCESSFULLY_CREATED = "Successfully created table";
    String TABLE_SUCCESSFULLY_UPDATED = "Successfully updated table";
    String SECTION_NAME_NOT_EXIST = "Section with name %s, does not exist";
    String SECTION_ID_NOT_EXIST = "Section with id %d does not exist";
    String FLOOR_ID_NOT_EXIST = "Floor number %d does not exist";
    String FLOOR_DUPLICATION = "Floor number %d already exist";
    String SECTION_DUPLICATION = "Section with name %s already exists";
    String SECTION_SUCCESSFULLY_UPDATED = "Successfully updated section";
    String SECTION_SUCCESSFULLY_CREATED = "Successfully created section";
    String MENU_SUCCESSFULLY_CREATED = "Successfully created menu";
    String MENU_TYPE_NOT_VALID = "Menu type %s is not valid";
    String TABLE_NOT_FOUND = "Table with id %d was not found";
    String MENU_ITEM_SUCCESSFULLY_CREATED = "Successfully created menu item";
    String MENU_NOT_FOUND = "Menu with id %d was not found";
    String NOT_AVAILABLE = "N/A";
    String BOTH_MISSING_MESSAGE = "Both product and recipe are missing. Please provide either a valid product or recipe for the menu item.";
    String BOTH_PROVIDED_MESSAGE = "Both product and recipe are provided. Please provide only one of them.";
    String INVALID_RESERVATION_PERSON_TYPE = "Invalid Reservation person type %s";
    String GLOBAL_EXCEPTION_PRODUCT_NOT_FOUND = "Product not found";
    String GLOBAL_EXCEPTION_DUPLICATED_PRODUCT = "Duplicated product";
    String GLOBAL_EXCEPTION_RECIPE_NOT_FOUND = "Recipe not found";
    String GLOBAL_EXCEPTION_PRODUCT_DETAILS_ERROR = "Product details error";
    String GLOBAL_EXCEPTION_RECIPE_DETAILS_ERROR = "Recipe details error";
    String GLOBAL_EXCEPTION_SUPPLIER_NOT_FOUND = "Supplier not found";
    String GLOBAL_EXCEPTION_DUPLICATED_SUPPLIER = "Duplicated supplier";
    String GLOBAL_EXCEPTION_UNEXPECTED_ERROR = "Unexpected error";
    String GLOBAL_EXCEPTION_MISMATCH_ID = "Mismatched object ID";
    String GLOBAL_EXCEPTION_DUPLICATED_RECIPE_PRODUCT = "Duplicated products in the input request";
    String GLOBAL_EXCEPTION_MISSING_INVENTORY_PRODUCT = "Missing product in the inventory";
    String GLOBAL_EXCEPTION_VALIDATION_ERROR = "Validation error";
    String GLOBAL_EXCEPTION_AUTHORIZATION = "Not authorized";
    String GLOBAL_EXCEPTION_MISSING_SECTION = "Section not found";
    String GLOBAL_EXCEPTION_TABLE_NOT_FOUND = "Table not found";
    String GLOBAL_EXCEPTION_SECTION_DUPLICATION = "Section duplication";
    String GLOBAL_EXCEPTION_FLOOR_DUPLICATION = "Floor duplication";
    String GLOBAL_EXCEPTION_MISSING_FLOOR = "Floor does not exist";
    String GLOBAL_EXCEPTION_MENU_DETAILS_ERROR = "Menu details error";
    String GLOBAL_EXCEPTION_MISSING_MENU_ITEM_DETAILS = "Missing input details";
}
