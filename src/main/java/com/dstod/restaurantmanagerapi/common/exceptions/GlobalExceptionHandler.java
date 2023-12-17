package com.dstod.restaurantmanagerapi.common.exceptions;

import com.dstod.restaurantmanagerapi.common.exceptions.auth.RefreshTokenFailureException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
import com.dstod.restaurantmanagerapi.common.exceptions.management.*;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.ErrorDetails;
import com.dstod.restaurantmanagerapi.common.models.FailureResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<FailureResponse> handleRuntimeException(RuntimeException exception) {
        return handleException(exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<FailureResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(new FailureResponse(ApplicationMessages.GLOBAL_EXCEPTION_VALIDATION_ERROR, new Date(), errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RefreshTokenFailureException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<FailureResponse> handleRefreshTokenFailureException(RefreshTokenFailureException e) {
        ArrayList<String> errors = new ArrayList<>();
        errors.add(e.getMessage());
        return new ResponseEntity<>(new FailureResponse(ApplicationMessages.GLOBAL_EXCEPTION_AUTHORIZATION, new Date(), errors), HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<FailureResponse> handleException(RuntimeException exception) {
        List<String> errors = new ArrayList<>();

        if (exception instanceof ProductNotFoundException) {
            List<Long> missingProducts = ((ProductNotFoundException) exception).getMissingProducts();
            missingProducts.forEach(p -> errors.add(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, p)));
        }

        if (exception instanceof InventoryStockIssuesException) {
            List<String> issues = ((InventoryStockIssuesException) exception).getIssues();
            errors.addAll(issues);
        }

        if (errors.isEmpty()) {
            errors.add(exception.getMessage());
        }

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
        } else if (exception instanceof MismatchedObjectIdException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_MISMATCH_ID, HttpStatus.CONFLICT);
        } else if (exception instanceof RecipeCategoryNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_RECIPE_DETAILS_ERROR, HttpStatus.CONFLICT);
        } else if (exception instanceof RecipeNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_RECIPE_NOT_FOUND, HttpStatus.CONFLICT);
        } else if (exception instanceof RecipeProductDuplicationException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_DUPLICATED_RECIPE_PRODUCT, HttpStatus.CONFLICT);
        } else if (exception instanceof InventoryStockIssuesException) {
            return new ErrorDetails(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } else if (exception instanceof InventoryProductNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_MISSING_INVENTORY_PRODUCT, HttpStatus.NOT_FOUND);
        } else if (exception instanceof SectionDoesNotExistException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_MISSING_SECTION, HttpStatus.NOT_FOUND);
        } else if (exception instanceof TableNotFoundException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_TABLE_NOT_FOUND, HttpStatus.NOT_FOUND);
        } else if (exception instanceof SectionDuplicationException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_SECTION_DUPLICATION, HttpStatus.CONFLICT);
        } else if (exception instanceof FloorDoesNotExistException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_MISSING_FLOOR, HttpStatus.NOT_FOUND);
        } else if (exception instanceof FloorDuplicationException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_FLOOR_DUPLICATION, HttpStatus.CONFLICT);
        } else if (exception instanceof MenuTypeNotExistException) {
            return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_MENU_DETAILS_ERROR, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ErrorDetails(ApplicationMessages.GLOBAL_EXCEPTION_UNEXPECTED_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
