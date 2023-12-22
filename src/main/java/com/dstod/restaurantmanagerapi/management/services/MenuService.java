package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.management.MenuTypeNotExistException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.*;
import com.dstod.restaurantmanagerapi.management.models.entities.Menu;
import com.dstod.restaurantmanagerapi.management.models.enums.MenuType;
import com.dstod.restaurantmanagerapi.management.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.*;

@Service
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public SuccessResponse createMenu(CreateMenuRequest request) {
        validateMenuType(request.menuType());

        Menu menu = createMenuEntity(request);

        Menu savedMenu = menuRepository.save(menu);

        BaseMenuInfoDto baseMenuInfoDto = mapToBaseMenuInfoDto(savedMenu);

        return new SuccessResponse(MENU_SUCCESSFULLY_CREATED, new Date(), baseMenuInfoDto);
    }

    public MenuDto getMenu() {
        List<Menu> menus = this.menuRepository.findByMenuTypeIn(Arrays.stream(MenuType.values()).toList());
        List<MenuItemCategoryDto> menuItemCategories = new ArrayList<>();
        menus.forEach(m -> {
            MenuItemCategoryDto menuItemCategoryDto = new MenuItemCategoryDto(m.getMenuType().name(), new ArrayList<>());
            m.getMenuItems().forEach(i -> {
                if (i.getRecipe() != null) {
                    menuItemCategoryDto.items().add(new MenuItemDto(i.getRecipe().getName(), i.getPrice(), i.getAdditionalInformation()));
                }
                if (i.getProduct() != null) {
                    menuItemCategoryDto.items().add(new MenuItemDto(i.getProduct().getName(), i.getPrice(), i.getAdditionalInformation()));
                }
            });
            menuItemCategories.add(menuItemCategoryDto);
        });

        return new MenuDto(menuItemCategories);
    }

    private static void validateMenuType(String menuType) {
        if (!MenuType.isValidMenuType(menuType)) {
            throw new MenuTypeNotExistException(String.format(MENU_TYPE_NOT_VALID, menuType));
        }
    }

    private Menu createMenuEntity(CreateMenuRequest request) {
        return new Menu (
                0L,
                MenuType.valueOf(request.menuType()),
                new ArrayList<>(),
                1.0,
                new Date(),
                null
        );
    }

    private BaseMenuInfoDto mapToBaseMenuInfoDto(Menu menu) {
        return new BaseMenuInfoDto(
                menu.getId(),
                menu.getMenuType().name(),
                menu.getRevision(),
                menu.getLastUpdate(),
                menu.getUpdate_comments() != null ? menu.getUpdate_comments() : NOT_AVAILABLE
        );
    }
}
