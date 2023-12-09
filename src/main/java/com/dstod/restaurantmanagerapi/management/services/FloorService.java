package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.management.repositories.FloorRepository;
import org.springframework.stereotype.Service;

@Service
public class FloorService {
    private final FloorRepository floorRepository;

    public FloorService(FloorRepository floorRepository) {
        this.floorRepository = floorRepository;
    }
}
