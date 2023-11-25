package com.dstod.restaurantmanagerapi.common.exceptions;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserRoleDoesNotExistException;
import com.dstod.restaurantmanagerapi.common.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotExistingProductException(ProductNotFoundException exception) {
        return sendResponseMessage("Inventory", exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDetailsDuplicationException.class)
    public ResponseEntity<ErrorResponse> handleUserDetailsDuplicationException(UserDetailsDuplicationException exception) {
        return sendResponseMessage("Users", exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return sendResponseMessage("Users", exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserRoleDoesNotExistException.class)
    public ResponseEntity<ErrorResponse> handleUserRoleDoesNotExistException(UserRoleDoesNotExistException exception) {
        return sendResponseMessage("Users", exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> sendResponseMessage(String module, String message, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(module, message), status);
    }
}
