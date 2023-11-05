package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.Inventory;
import com.dstod.restaurantmanagerapi.inventory.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
}
