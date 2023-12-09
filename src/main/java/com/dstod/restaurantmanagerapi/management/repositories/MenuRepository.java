package com.dstod.restaurantmanagerapi.management.repositories;

import com.dstod.restaurantmanagerapi.management.models.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
