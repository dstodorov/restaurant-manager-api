package com.dstod.restaurantmanagerapi.inventory.models.enums;

import java.util.Arrays;

public enum UnitType {
    KG, GR, ML, L;

    public static boolean isValidUnit(String unitName) {
        return Arrays.stream(UnitType.values()).anyMatch(unit -> unit.name().equalsIgnoreCase(unitName));
    }
}
