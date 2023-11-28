package com.dstod.restaurantmanagerapi.common.exceptions;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.ErrorDetails;
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

        ErrorDetails errorDetails = getErrorDetails(exception);

        return new ResponseEntity<>(new FailureResponse(errorDetails.message(), new Date(), errors), errorDetails.status());
    }

    private ErrorDetails getErrorDetails(RuntimeException exception) {
        if (exception instanceof ProductNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_PRODUCT_NOT_FOUND, HttpStatus.NOT_FOUND);
        } else if (exception instanceof DuplicatedProductException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_DUPLICATED_PRODUCT, HttpStatus.CONFLICT);
        } else if (exception instanceof ProductUnitNotFoundException || exception instanceof ProductCategoryNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_PRODUCT_DETAILS_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (exception instanceof SupplierNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_SUPPLIER_NOT_FOUND, HttpStatus.NOT_FOUND);
        } else if (exception instanceof DuplicatedSupplierDetailsException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_DUPLICATED_SUPPLIER, HttpStatus.CONFLICT);
        }
        return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
