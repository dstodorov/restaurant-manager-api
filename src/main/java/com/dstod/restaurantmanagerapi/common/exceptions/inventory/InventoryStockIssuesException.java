package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class InventoryStockIssuesException extends RMException {
    private List<String> issues = new ArrayList<>();
    public InventoryStockIssuesException(String message, List<String> issues) {
        super(message, message, HttpStatus.BAD_REQUEST);
        this.issues = issues;
    }

    public List<String> getIssues() {
        return issues;
    }
}
