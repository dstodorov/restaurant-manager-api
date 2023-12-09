package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.management.repositories.MenuItemRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuItemsService {
    private final MenuItemRepository menuItemRepository;

    public MenuItemsService(MenuItemRepository menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }
}
