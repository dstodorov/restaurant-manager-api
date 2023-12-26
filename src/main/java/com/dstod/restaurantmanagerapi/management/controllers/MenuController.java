package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.*;
import com.dstod.restaurantmanagerapi.management.services.MenuItemService;
import com.dstod.restaurantmanagerapi.management.services.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/menu")
@Tag(name = "Manage menu APIs")
public class MenuController {

    private final MenuService menuService;
    private final MenuItemService menuItemService;

    public MenuController(MenuService menuService, MenuItemService menuItemService) {
        this.menuService = menuService;
        this.menuItemService = menuItemService;
    }

    @Operation(summary = "Create new menu")
    @PostMapping
    public ResponseEntity<SuccessResponse> createMenu(@Valid @RequestBody CreateMenuRequest request, UriComponentsBuilder uri) {
        SuccessResponse successResponse = menuService.createMenu(request);
        BaseMenuInfoDto baseMenuInfoDto = (BaseMenuInfoDto) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/menu/{id}").buildAndExpand(baseMenuInfoDto.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }

    @Operation(summary = "Add new item (recipe / product) to the menu")
    @PostMapping("/item")
    public ResponseEntity<SuccessResponse> createMenuItem(@Valid @RequestBody CreateMenuItemRequest request, UriComponentsBuilder uri) {
        SuccessResponse successResponse = menuItemService.createMenuItem(request);
        CreateMenuItemResponse createMenuItemResponse = (CreateMenuItemResponse) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/menu/item/{id}").buildAndExpand(createMenuItemResponse.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }

    @Operation(summary = "Get current menu")
    @GetMapping
    public ResponseEntity<MenuDto> getMenu() {
        return ResponseEntity.ok(this.menuService.getMenu());
    }
}
