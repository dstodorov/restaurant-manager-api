package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.management.MenuTypeNotExistException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.BaseMenuInfoDto;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateMenuRequest;
import com.dstod.restaurantmanagerapi.management.models.entities.Menu;
import com.dstod.restaurantmanagerapi.management.models.enums.MenuType;
import com.dstod.restaurantmanagerapi.management.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

import static com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages.NOT_AVAILABLE;

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

        return new SuccessResponse("Successfully created menu", new Date(), baseMenuInfoDto);
    }

    private static void validateMenuType(String menuType) {
        if (!MenuType.isValidMenuType(menuType)) {
            throw new MenuTypeNotExistException(String.format("Menu type %s is not valid", menuType));
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
