package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.management.repositories.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }
}
