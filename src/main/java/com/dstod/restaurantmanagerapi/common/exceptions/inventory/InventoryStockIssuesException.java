package com.dstod.restaurantmanagerapi.common.exceptions.inventory;

import java.util.List;

public class InventoryStockIssuesException extends RuntimeException {
    private List<String> issues;
    public InventoryStockIssuesException(String message, List<String> issues) {
        super(message);
        this.issues = issues;
    }

    public List<String> getIssues() {
        return issues;
    }
}
