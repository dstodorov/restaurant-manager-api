package com.dstod.restaurantmanagerapi.common.exceptions;

import com.dstod.restaurantmanagerapi.common.exceptions.auth.RefreshTokenFailureException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
import com.dstod.restaurantmanagerapi.common.models.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailureResponse> handleRuntimeException(RuntimeException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(RMException.class)
    public ResponseEntity<FailureResponse> handleRMException(RMException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FailureResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(new FailureResponse(GLOBAL_EXCEPTION_VALIDATION_ERROR, new Date(), errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenFailureException.class)
    public ResponseEntity<FailureResponse> handleRefreshTokenFailureException(RefreshTokenFailureException e) {
        ArrayList<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return new ResponseEntity<>(new FailureResponse(GLOBAL_EXCEPTION_AUTHORIZATION, new Date(), errors), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<FailureResponse> handleException(RuntimeException exception) {
        List<String> errors = new ArrayList<>();
        errors.add(exception.getMessage());
        return new ResponseEntity<>(new FailureResponse(GLOBAL_EXCEPTION_UNEXPECTED_ERROR, new Date(), errors), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<FailureResponse> handleException(RMException exception) {
        List<String> errors = new ArrayList<>();

        if (exception instanceof ProductNotFoundException) {
            List<Long> missingProducts = ((ProductNotFoundException) exception).getMissingProducts();
            missingProducts.forEach(p -> errors.add(String.format(PRODUCT_NOT_FOUND, p)));
        }

        if (exception instanceof InventoryStockIssuesException) {
            List<String> issues = ((InventoryStockIssuesException) exception).getIssues();
            errors.addAll(issues);
        }

        if (errors.isEmpty()) {
            errors.add(exception.getMessage());
        }

        return new ResponseEntity<>(new FailureResponse(exception.getGlobalMessage(), new Date(), errors), exception.getStatus());
    }
}
