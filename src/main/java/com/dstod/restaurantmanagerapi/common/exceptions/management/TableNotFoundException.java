package com.dstod.restaurantmanagerapi.common.exceptions.management;

import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;

public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String message) {
        super(message);
    }
}
