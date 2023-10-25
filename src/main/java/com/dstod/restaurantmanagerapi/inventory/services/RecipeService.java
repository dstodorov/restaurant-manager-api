package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.core.exceptions.inventory.DuplicatedRecipeException;
import com.dstod.restaurantmanagerapi.core.exceptions.inventory.RecipeNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.Recipe;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.RecipeDTO;
import com.dstod.restaurantmanagerapi.inventory.models.enums.RecipeCategory;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Long createRecipe(RecipeDTO recipeDTO) {
        Optional<Recipe> recipeByName = this.recipeRepository.findByName(recipeDTO.name());

        if (recipeByName.isPresent()) {
            return -1L;
        }

        Recipe recipe = new Recipe(
                0L,
                recipeDTO.name(),
                RecipeCategory.valueOf(recipeDTO.category())
        );

        return this.recipeRepository.save(recipe).getId();
    }


    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {

        // Product not found, throw exception
        Recipe recipeById = this.recipeRepository
                .findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id.toString()));

        // Duplicated recipe name, throw exception
        if (this.recipeRepository
                .findByName(recipeDTO.name())
                .filter(recipe -> !recipe.getId().equals(id)).isPresent()) {
            throw new DuplicatedRecipeException(id.toString());
        }

        // Saving changes
        recipeById.setName(recipeDTO.name());
        recipeById.setCategory(RecipeCategory.valueOf(recipeDTO.category()));

        this.recipeRepository.save(recipeById);

        return new RecipeDTO(
                id,
                recipeDTO.name(),
                recipeDTO.category()
        );
    }

    public Optional<RecipeDTO> getRecipe(Long id) {
        return this.recipeRepository.findById(id).map(this::mapToRecipeDTO);
    }

    public Optional<List<RecipeDTO>> getAllRecipes() {
        if (this.recipeRepository.count() == 0) {
            return Optional.empty();
        }

        return Optional.of(this.recipeRepository
                .findAll()
                .stream()
                .map(this::mapToRecipeDTO)
                .toList());
    }

    private RecipeDTO mapToRecipeDTO(Recipe recipe) {
        return new RecipeDTO(
                recipe.getId(),
                recipe.getName(),
                recipe.getCategory().name()
        );
    }
}
