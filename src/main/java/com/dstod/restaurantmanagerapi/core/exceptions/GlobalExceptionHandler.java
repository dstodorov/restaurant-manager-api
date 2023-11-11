package com.dstod.restaurantmanagerapi.core.exceptions;

import com.dstod.restaurantmanagerapi.inventory.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotExistingProductException(ProductNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse("Inventory", exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
