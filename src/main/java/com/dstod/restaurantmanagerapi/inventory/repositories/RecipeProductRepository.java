package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import com.dstod.restaurantmanagerapi.inventory.models.entities.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {
    List<RecipeProduct> findRecipeProductByRecipe(Recipe recipe);
    Optional<RecipeProduct> findByRecipeAndProduct(Recipe recipe, Product product);
}
