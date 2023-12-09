package com.dstod.restaurantmanagerapi.management.repositories;

import com.dstod.restaurantmanagerapi.management.models.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Long> {
}
