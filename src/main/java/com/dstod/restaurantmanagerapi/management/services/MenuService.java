package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.management.MenuTypeNotExistException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateMenuRequest;
import com.dstod.restaurantmanagerapi.management.models.enums.MenuType;
import com.dstod.restaurantmanagerapi.management.repositories.MenuRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public SuccessResponse createMenu(CreateMenuRequest request) {
        if (!MenuType.isValidMenuType(request.menuType())) {
            throw new MenuTypeNotExistException(String.format("Menu type %s is not valid", request.menuType()));
        }


        return null;
    }
}
