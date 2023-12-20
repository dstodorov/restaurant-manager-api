package com.dstod.restaurantmanagerapi.management.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.RecipeNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.management.InvalidMenuItemInputException;
import com.dstod.restaurantmanagerapi.common.exceptions.management.MenuNotFoundException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeRepository;
import com.dstod.restaurantmanagerapi.management.models.dtos.*;
import com.dstod.restaurantmanagerapi.management.models.entities.Menu;
import com.dstod.restaurantmanagerapi.management.models.entities.MenuItem;
import com.dstod.restaurantmanagerapi.management.models.enums.MenuItemType;
import com.dstod.restaurantmanagerapi.management.repositories.MenuItemRepository;
import com.dstod.restaurantmanagerapi.management.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MenuItemService {
    private static final String BOTH_MISSING_MESSAGE = "Both product and recipe are missing. Please provide either a valid product or recipe for the menu item.";
    private static final String BOTH_PROVIDED_MESSAGE = "Both product and recipe are provided. Please provide only one of them.";

    private final MenuItemRepository menuItemRepository;
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;
    private final RecipeRepository recipeRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, MenuRepository menuRepository, ProductRepository productRepository, RecipeRepository recipeRepository) {
        this.menuItemRepository = menuItemRepository;
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
        this.recipeRepository = recipeRepository;
    }

    public SuccessResponse createMenuItem(CreateMenuItemRequest request) {

        validateMenuItemInput(request);
        MenuItemType itemType = getMenuItemType(request);

        Product product = null;
        Recipe recipe = null;

        switch (itemType) {
            case PRODUCT -> product = productRepository.findById(request.productId()).orElseThrow(
                    () -> new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, request.productId()))
            );
            case RECIPE -> recipe = recipeRepository.findById(request.recipeId()).orElseThrow(
                    () -> new RecipeNotFoundException(String.format(ApplicationMessages.RECIPE_NOT_FOUND, request.recipeId()))
            );
        }

        Menu menu = this.menuRepository.findById(request.menuId()).orElseThrow(
                () -> new MenuNotFoundException(String.format("Menu with id %d was not found", request.menuId()))
        );

        MenuItem menuItem = createMenuItemEntity(request, product, recipe, menu);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        CreateMenuItemResponse createMenuItemResponse = mapToCreateMenuItemResponse(savedMenuItem);

        return new SuccessResponse("Successfully created menu item", new Date(), createMenuItemResponse);
    }

    private CreateMenuItemResponse mapToCreateMenuItemResponse(MenuItem menuItem) {
        return new CreateMenuItemResponse(
                menuItem.getId(),
                menuItem.getPrice(),
                menuItem.getAdditionalInformation(),
                new BaseMenuInfoDto(
                        menuItem.getMenu().getId(),
                        menuItem.getMenu().getMenuType().name(),
                        menuItem.getMenu().getRevision(),
                        menuItem.getMenu().getLastUpdate(),
                        menuItem.getMenu().getUpdate_comments()
                ),
                Optional.ofNullable(menuItem.getProduct())
                        .map(product -> new ProductInfoDto(product.getId(), product.getName()))
                        .orElse(null),
                Optional.ofNullable(menuItem.getRecipe())
                        .map(recipe -> new RecipeInfoDto(recipe.getId(), recipe.getName()))
                        .orElse(null)
        );
    }

    private MenuItem createMenuItemEntity(CreateMenuItemRequest request, Product product, Recipe recipe, Menu menu) {
        return new MenuItem(
                0L,
                request.price(),
                request.additionalInformation(),
                menu,
                recipe,
                product
        );
    }

    private MenuItemType getMenuItemType(CreateMenuItemRequest request) {
        if (request.productId() != null) {
            return MenuItemType.PRODUCT;
        }

        return MenuItemType.RECIPE;
    }

    private void validateMenuItemInput(CreateMenuItemRequest request) {
        if (request.productId() == null && request.recipeId() == null) {
            throw new InvalidMenuItemInputException(BOTH_MISSING_MESSAGE);
        }

        if (request.productId() != null && request.recipeId() != null) {
            throw new InvalidMenuItemInputException(BOTH_PROVIDED_MESSAGE);
        }
    }
}
