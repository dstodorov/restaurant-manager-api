package com.dstod.restaurantmanagerapi.management.repositories;

import com.dstod.restaurantmanagerapi.management.models.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
}
