package com.dstod.restaurantmanagerapi.core.exceptions;

import com.dstod.restaurantmanagerapi.inventory.exceptions.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.users.exceptions.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.users.exceptions.UserNotFoundException;
import com.dstod.restaurantmanagerapi.users.exceptions.UserRoleDoesNotExistException;
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

    @ExceptionHandler(UserDetailsDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleUserDetailsDuplicationException(UserDetailsDuplicationException exception) {
        return new ResponseEntity<>(new ErrorResponse("Users", exception.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse("Users", exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRoleDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserRoleDoesNotExistException(UserRoleDoesNotExistException exception) {
        return new ResponseEntity<>(new ErrorResponse("Users", exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
