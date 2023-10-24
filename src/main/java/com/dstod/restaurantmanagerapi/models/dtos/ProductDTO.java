package com.dstod.restaurantmanagerapi.models.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProductDTO(Long id,
                         @NotNull
                         @Size(min = 2)
                         String name,
                         @Pattern(regexp = "^MEAT$|^SEAFOOD$|^VEGETABLE$|^FRUIT$|^GRAINS$|^DIARY$|^NUT_SEED$|^HERB_SPICE$|^ALCOHOLIC_DRINK$|^NON_ALCOHOLIC_DRINK$")
                         String category,
                         @Pattern(regexp = "^KG$|^GR$|^ML$|^L$")
                         String unit) {
}