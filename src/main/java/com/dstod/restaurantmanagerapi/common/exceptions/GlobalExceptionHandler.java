package com.dstod.restaurantmanagerapi.common.exceptions;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailureResponse> handleRuntimeException(RuntimeException exception) {
        return handleException(exception);
    }
//
//    @ExceptionHandler(UserDetailsDuplicationException.class)
//    public ResponseEntity<ErrorResponse> handleUserDetailsDuplicationException(UserDetailsDuplicationException exception) {
//        return sendResponseMessage("Users", exception.getMessage(), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
//        return sendResponseMessage("Users", exception.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(UserRoleDoesNotExistException.class)
//    public ResponseEntity<ErrorResponse> handleUserRoleDoesNotExistException(UserRoleDoesNotExistException exception) {
//        return sendResponseMessage("Users", exception.getMessage(), HttpStatus.NOT_FOUND);
//    }
//
//    private ResponseEntity<ErrorResponse> sendResponseMessage(String module, String message, HttpStatus status) {
//        return new ResponseEntity<>(new ErrorResponse(module, message), status);
//    }

    private ResponseEntity<FailureResponse> handleException(RuntimeException exception) {
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());

        String specificMessage = getSpecificMessage(exception);
        HttpStatus specificStatus = getSpecificStatus(exception, HttpStatus.INTERNAL_SERVER_ERROR);

        if (specificMessage != null) {
            return new ResponseEntity<>(new FailureResponse(specificMessage, new Date(), errors), specificStatus);
        }

        return new ResponseEntity<>(new FailureResponse("Unexpected error", new Date(), errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getSpecificMessage(RuntimeException exception) {
        if (exception instanceof ProductNotFoundException) {
            return "Product not found";
        } else if (exception instanceof DuplicatedProductException) {
            return "Duplicated product";
        } else if (exception instanceof ProductUnitNotFoundException) {
            return "Product details error";
        } else if (exception instanceof ProductCategoryNotFoundException) {
            return "Product details error";
        } else if (exception instanceof SupplierNotFoundException) {
            return "Supplier not found";
        } else if (exception instanceof DuplicatedSupplierDetailsException) {
            return "Supplier details duplication";
        }
        return null;
    }

    private HttpStatus getSpecificStatus(RuntimeException exception, HttpStatus defaultStatus) {
        if (exception instanceof ProductNotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (exception instanceof DuplicatedProductException || exception instanceof DuplicatedSupplierDetailsException) {
            return HttpStatus.CONFLICT;
        } else if (exception instanceof ProductUnitNotFoundException || exception instanceof ProductCategoryNotFoundException) {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (exception instanceof SupplierNotFoundException) {
            return HttpStatus.NOT_FOUND;
        }
        return defaultStatus;
    }
}
