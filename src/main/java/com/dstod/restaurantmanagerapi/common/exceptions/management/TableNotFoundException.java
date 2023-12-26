package com.dstod.restaurantmanagerapi.common.exceptions.management;

import com.dstod.restaurantmanagerapi.common.exceptions.RMException;
import org.aspectj.apache.bcel.classfile.annotation.RuntimeInvisAnnos;
import org.springframework.http.HttpStatus;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.GLOBAL_EXCEPTION_TABLE_NOT_FOUND;

public class TableNotFoundException extends RMException {
    public TableNotFoundException(String message) {
        super(message, GLOBAL_EXCEPTION_TABLE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
