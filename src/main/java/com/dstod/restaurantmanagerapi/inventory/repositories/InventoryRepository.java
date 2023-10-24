package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
