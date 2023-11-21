package com.dstod.restaurantmanagerapi.users.models.enums;

import com.dstod.restaurantmanagerapi.users.exceptions.UserRoleDoesNotExistException;

import java.util.Arrays;

public enum RoleType {
    ADMIN,
    CEO,
    RESTAURANT_MANAGER,
    ASSISTANT_MANAGER,
    WAITER,
    HOST,
    BARTENDER,
    BARBACK,
    BARISTA,
    BUSSER,
    HEAD_CHEF,
    SOUS_CHEF,
    PREP_COOK,
    LINE_COOK,
    DISHWASHER;

    public static RoleType fromString(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.name().equalsIgnoreCase(value)) {
                return roleType;
            }
        }

        throw new UserRoleDoesNotExistException(String.format("User role %s, is not a valid User Role!", value));
    }
}
