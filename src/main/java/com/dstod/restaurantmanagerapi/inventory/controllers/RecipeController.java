package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.RecipeDto;
import com.dstod.restaurantmanagerapi.inventory.services.RecipeService;

import com.dstod.restaurantmanagerapi.common.models.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recipes")
@Tag(name = "Manage recipe APIs")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(summary = "Create new recipe")
    @PostMapping
    public ResponseEntity<SuccessResponse> createRecipe(@Valid @RequestBody RecipeDto recipeDTO) {
        return ResponseEntity.ok(this.recipeService.createRecipe(recipeDTO));
    }

    @Operation(summary = "Get recipe info by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(this.recipeService.getRecipe(id));
    }

    @Operation(summary = "Get list of all recipes")
    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        Optional<List<RecipeDto>> allRecipes = this.recipeService.getAllRecipes();

        return allRecipes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Update recipe by given ID")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody RecipeDto recipeDTO) {
        return ResponseEntity.ok(this.recipeService.updateRecipe(id, recipeDTO));
    }
}
