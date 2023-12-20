package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.*;
import com.dstod.restaurantmanagerapi.management.services.MenuItemService;
import com.dstod.restaurantmanagerapi.management.services.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    private final MenuService menuService;
    private final MenuItemService menuItemService;

    public MenuController(MenuService menuService, MenuItemService menuItemService) {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createMenu(@Valid @RequestBody CreateMenuRequest request, UriComponentsBuilder uri) {
        SuccessResponse successResponse = menuService.createMenu(request);
        BaseMenuInfoDto baseMenuInfoDto = (BaseMenuInfoDto) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/menu/{id}").buildAndExpand(baseMenuInfoDto.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }

    @PostMapping("/item")
    public ResponseEntity<SuccessResponse> createMenuItem(@Valid @RequestBody CreateMenuItemRequest request, UriComponentsBuilder uri) {
        SuccessResponse successResponse = menuItemService.createMenuItem(request);
        CreateMenuItemResponse createMenuItemResponse = (CreateMenuItemResponse) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/menu/item/{id}").buildAndExpand(createMenuItemResponse.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }
}
