package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.DuplicatedRecipeException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.MismatchedObjectIdException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.RecipeCategoryNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.RecipeNotFoundException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.RecipeDto;
import com.dstod.restaurantmanagerapi.inventory.models.enums.RecipeCategory;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public SuccessResponse createRecipe(RecipeDto recipeDto) {

        ensureRecipeDoesNotExist(recipeDto.name());

        validateRecipeData(recipeDto);

        Recipe recipe = mapToRecipe(recipeDto);

        Recipe savedRecipe = this.recipeRepository.save(recipe);

        RecipeDto savedRecipeDto = mapToRecipeDTO(savedRecipe);

        return new SuccessResponse(ApplicationMessages.RECIPE_SUCCESSFULLY_CREATED, new Date(), savedRecipeDto);
    }

    public RecipeDto getRecipe(Long id) {
        return this.recipeRepository
                .findById(id)
                .map(this::mapToRecipeDTO)
                .orElseThrow(() ->
                        new RecipeNotFoundException(String.format(ApplicationMessages.RECIPE_NOT_FOUND, id)));
    }

    public Optional<List<RecipeDto>> getAllRecipes() {
        return Optional.of(this.recipeRepository
                .findAll()
                .stream()
                .map(this::mapToRecipeDTO)
                .toList()
        );
    }

    public SuccessResponse updateRecipe(Long id, RecipeDto recipeDto) {
        Recipe recipeById = this.recipeRepository
                .findById(id)
                .orElseThrow(() ->
                        new RecipeNotFoundException(String.format(ApplicationMessages.RECIPE_NOT_FOUND, id)));

        if (this.recipeRepository
                .findByName(recipeDto.name())
                .filter(recipe -> !recipe.getId().equals(id)).isPresent()) {
            throw new MismatchedObjectIdException(ApplicationMessages.MISMATCHED_OBJECT_EXCEPTION);
        }

        validateRecipeData(recipeDto);

        recipeById.setName(recipeDto.name());
        recipeById.setPreparationMethod(recipeDto.preparationMethod());
        recipeById.setCategory(RecipeCategory.valueOf(recipeDto.category().toUpperCase()));

        Recipe savedRecipe = this.recipeRepository.save(recipeById);

        RecipeDto savedRecipeDto = mapToRecipeDTO(savedRecipe);

        return new SuccessResponse(ApplicationMessages.RECIPE_SUCCESSFULLY_UPDATED, new Date(), savedRecipeDto);
    }

    private void validateRecipeData(RecipeDto recipeDto) {
        if (!RecipeCategory.isValidCategory(recipeDto.category())) {
            throw new RecipeCategoryNotFoundException(String.format(ApplicationMessages.RECIPE_WRONG_CATEGORY, recipeDto.category()));
        }
    }

    private Recipe mapToRecipe(RecipeDto recipeDto) {
        return new Recipe(
                0L,
                recipeDto.name(),
                recipeDto.preparationMethod(),
                RecipeCategory.valueOf(recipeDto.category())
        );
    }

    private void ensureRecipeDoesNotExist(String name) {
        Optional<Recipe> recipeByName = this.recipeRepository.findByName(name);

        if (recipeByName.isPresent()) {
            throw new DuplicatedRecipeException(String.format(ApplicationMessages.DUPLICATED_RECIPE, name));
        }
    }

    private RecipeDto mapToRecipeDTO(Recipe recipe) {
        return new RecipeDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getPreparationMethod(),
                recipe.getCategory().name()
        );
    }

}
