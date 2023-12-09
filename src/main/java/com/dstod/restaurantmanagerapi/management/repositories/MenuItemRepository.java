package com.dstod.restaurantmanagerapi.management.repositories;

import com.dstod.restaurantmanagerapi.management.models.entities.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
