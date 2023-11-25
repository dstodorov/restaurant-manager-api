package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import com.dstod.restaurantmanagerapi.inventory.models.entities.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {
    List<RecipeProduct> findRecipeProductByRecipe(Recipe recipe);
}
