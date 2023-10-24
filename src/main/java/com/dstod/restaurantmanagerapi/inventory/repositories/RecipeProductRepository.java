package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.RecipeProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeProductRepository extends JpaRepository<RecipeProduct, Long> {

}
