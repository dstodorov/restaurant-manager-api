package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.RecipeProductDuplicationException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.RecipeNotFoundException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.*;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import com.dstod.restaurantmanagerapi.inventory.models.entities.RecipeProduct;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeProductRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecipeProductsService {

    private final RecipeProductRepository recipeProductRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;
    private final RecipeService recipeService;

    public RecipeProductsService(RecipeProductRepository recipeProductRepository, RecipeRepository recipeRepository, ProductRepository productRepository, RecipeService recipeService) {
        this.recipeProductRepository = recipeProductRepository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
        this.recipeService = recipeService;
    }

    public SuccessResponse addRecipeProducts(Long recipeId, RecipeProductsDto productsDTO) {

        Map<Long, RecipeProductDto> productsMap = new HashMap<>();

        productsDTO.products().forEach(p -> {
            if (productsMap.containsKey(p.productId())) {
//                productsMap.put(p.productId(), new RecipeProductDto(
//                        p.productId(),
//                        productsMap.get(p.productId()).quantity() + p.quantity()
//                ));
                throw new RecipeProductDuplicationException(String.format(ApplicationMessages.RECIPE_PRODUCT_DUPLICATION, p.productId()));
            } else {
                productsMap.put(p.productId(), new RecipeProductDto(p.productId(), p.quantity()));
            }
        });

        List<Long> missingProducts = new ArrayList<>();

        productsMap.forEach((key, value) -> {
            Optional<Product> byId = this.productRepository.findById(key);
            if (byId.isEmpty()) {
                missingProducts.add(key);
            }
        });

        if (!missingProducts.isEmpty()) {
            throw new ProductNotFoundException(ApplicationMessages.PRODUCTS_NOT_FOUND, missingProducts);
        }

        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(String.format(ApplicationMessages.RECIPE_NOT_FOUND, recipeId));
        }

        List<RecipeProduct> productsList = productsMap.values()
                .stream()
                .map(recipeProductDto -> mapToRecipeProduct(recipe.get(), recipeProductDto))
                .toList();

        List<RecipeProduct> savedRecipeProducts = this.recipeProductRepository.saveAll(productsList);

        RecipeDetailsResponse recipeDetailsResponse = createRecipeDetailsResponse(recipe.get(), savedRecipeProducts);
        return new SuccessResponse(ApplicationMessages.RECIPE_PRODUCT_SUCCESSFULLY_UPDATED, new Date(), recipeDetailsResponse);
    }

    private RecipeDetailsResponse createRecipeDetailsResponse(Recipe recipe, List<RecipeProduct> recipeProducts) {
        RecipeDto recipeDto = recipeService.mapToRecipeDTO(recipe);

        List<RecipeProductInfoResponse> recipeProductInfoResponses = recipeProducts
                .stream()
                .map(this::mapToRecipeProductInfoResponse)
                .toList();

        return new RecipeDetailsResponse(recipeDto, recipeProductInfoResponses);
    }

    private RecipeProductInfoResponse mapToRecipeProductInfoResponse(RecipeProduct recipeProduct) {
        return new RecipeProductInfoResponse(recipeProduct.getProduct().getName(), recipeProduct.getQuantity());
    }

    private RecipeProduct mapToRecipeProduct(Recipe recipe, RecipeProductDto recipeProduct) {
        Optional<Product> productById = this.productRepository.findById(recipeProduct.productId());

        return new RecipeProduct(
                0L,
                productById.get(),
                recipe,
                recipeProduct.quantity()
        );
    }
}