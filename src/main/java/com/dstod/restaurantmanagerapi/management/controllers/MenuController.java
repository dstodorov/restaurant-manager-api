package com.dstod.restaurantmanagerapi.management.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.management.models.dtos.BaseMenuInfoDto;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateMenuRequest;
import com.dstod.restaurantmanagerapi.management.models.dtos.CreateSectionResponse;
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

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createMenu(@Valid @RequestBody CreateMenuRequest request, UriComponentsBuilder uri) {
        SuccessResponse successResponse = menuService.createMenu(request);
        BaseMenuInfoDto baseMenuInfoDto = (BaseMenuInfoDto) successResponse.savedObject();

        UriComponents uriComponents = uri.path("/menu/{id}").buildAndExpand(baseMenuInfoDto.id());
        return ResponseEntity.created(uriComponents.toUri()).body(successResponse);
    }


}
