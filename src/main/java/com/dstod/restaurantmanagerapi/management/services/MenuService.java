package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.management.repositories.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
}
