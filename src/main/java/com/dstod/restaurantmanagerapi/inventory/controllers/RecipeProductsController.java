package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.RecipeProductsDto;
import com.dstod.restaurantmanagerapi.inventory.services.RecipeProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recipeProducts")
@Tag(name = "Manage recipe products APIs")
public class RecipeProductsController {

    private final RecipeProductsService recipeProductsService;

    public RecipeProductsController(RecipeProductsService recipeProductsService) {
        this.recipeProductsService = recipeProductsService;
    }

    @Operation(summary = "Add new product to specific recipe by given ID")
    @PostMapping("/{recipeId}")
    public ResponseEntity<SuccessResponse> addRecipeProducts(@PathVariable Long recipeId, @Valid @RequestBody RecipeProductsDto recipeProductsDTO) {
        return ResponseEntity.ok(this.recipeProductsService.addRecipeProducts(recipeId, recipeProductsDTO));
    }
}
