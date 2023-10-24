package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);
}
