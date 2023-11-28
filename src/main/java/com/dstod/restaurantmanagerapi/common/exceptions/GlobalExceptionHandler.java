package com.dstod.restaurantmanagerapi.common.exceptions;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserDetailsDuplicationException;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.users.UserRoleDoesNotExistException;
import com.dstod.restaurantmanagerapi.common.models.ErrorResponse;
import com.dstod.restaurantmanagerapi.common.models.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            ProductNotFoundException.class,
            DuplicatedProductException.class,
            ProductCategoryNotFoundException.class,
            ProductUnitNotFoundException.class
    })
    public ResponseEntity<FailureResponse> handleException(RuntimeException exception) {
        return handle(exception);
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

    @ExceptionHandler(DuplicatedSupplierDetailsException.class)
    public ResponseEntity<ErrorResponse> handleDuplicatedSupplierDetailsException(DuplicatedSupplierDetailsException exception) {
        return sendResponseMessage("Inventory", exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplierNotFoundException(SupplierNotFoundException exception) {
        return sendResponseMessage("Inventory", exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> sendResponseMessage(String module, String message, HttpStatus status) {
        return new ResponseEntity<>(new ErrorResponse(module, message), status);
    }

    private ResponseEntity<FailureResponse> handle(RuntimeException exception) {
        List<String> errors = new ArrayList<>();

        errors.add(exception.getMessage());

        if (exception instanceof ProductNotFoundException) {
            return new ResponseEntity<>(new FailureResponse("Not existing product", new Date(), errors), HttpStatus.NOT_FOUND);
        } else if (exception instanceof DuplicatedProductException) {
            return new ResponseEntity<>(new FailureResponse("Duplicated product", new Date(), errors), HttpStatus.CONFLICT);
        }else if (exception instanceof ProductUnitNotFoundException) {
            return new ResponseEntity<>(new FailureResponse("Product details error", new Date(), errors), HttpStatus.UNPROCESSABLE_ENTITY);
        }else if (exception instanceof ProductCategoryNotFoundException) {
            return new ResponseEntity<>(new FailureResponse("Product details error", new Date(), errors), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return null;
    }
}
