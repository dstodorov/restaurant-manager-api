package com.dstod.restaurantmanagerapi.management.repositories;

import com.dstod.restaurantmanagerapi.management.models.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
}
