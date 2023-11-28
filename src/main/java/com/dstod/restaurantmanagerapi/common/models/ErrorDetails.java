package com.dstod.restaurantmanagerapi.common.models;

import org.springframework.http.HttpStatus;

public record ErrorDetails (String message, HttpStatus status){

}
