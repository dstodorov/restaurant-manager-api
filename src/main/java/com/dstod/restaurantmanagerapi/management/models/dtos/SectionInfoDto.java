package com.dstod.restaurantmanagerapi.management.models.dtos;

import java.util.List;

public record SectionInfoDto(
        Long id,
        String sectionName,
        Integer floor,
        Boolean active,
        List<TableInfoDto> tables
) {
}
