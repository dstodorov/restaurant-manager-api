package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.messages.RmMessages;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.RecipeNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import com.dstod.restaurantmanagerapi.inventory.models.entities.RecipeProduct;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.RecipeProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.RecipeProductsDTO;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeProductRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeProductsService {

    private final RecipeProductRepository recipeProductRepository;
    private final RecipeRepository recipeRepository;
    private final ProductRepository productRepository;

    public RecipeProductsService(RecipeProductRepository recipeProductRepository, RecipeRepository recipeRepository, ProductRepository productRepository) {
        this.recipeProductRepository = recipeProductRepository;
        this.recipeRepository = recipeRepository;
        this.productRepository = productRepository;
    }

    public Long addRecipeProducts(Long recipeId, RecipeProductsDTO productsDTO) {

        Map<Long, RecipeProductDTO> products = new HashMap<>();

        productsDTO.products().forEach(p -> {
            if (products.containsKey(p.productId())) {
                products.put(p.productId(), new RecipeProductDTO(
                        p.productId(),
                        products.get(p.productId()).quantity() + p.quantity()
                ));
            } else {
                products.put(p.productId(), new RecipeProductDTO(p.productId(), p.quantity()));
            }
        });

        Set<Long> productsById = productsDTO.products().stream().map(RecipeProductDTO::productId).collect(Collectors.toSet());

        // In case of missing products, throw exception

        /*if (productsById.size() != productsDTO.products().size()) {
            throw new ProductNotFoundException("To be refactored!");
        }*/

        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            throw new RecipeNotFoundException(recipeId.toString());
        }

        List<RecipeProduct> productsList = productsDTO.products()
                .stream()
                .map(p -> mapToRecipeProduct(recipe.get(), p))
                .toList();

        this.recipeProductRepository.saveAll(productsList);

        return recipeId;
    }

    private RecipeProduct mapToRecipeProduct(Recipe recipe, RecipeProductDTO recipeProduct) {
        Optional<Product> productById = this.productRepository.findById(recipeProduct.productId());

        // Not needed check, but made just to remove null waring on productById.get() line below
        // Its guaranteed this product exists in counter check above
        if (productById.isEmpty()) {
            throw new ProductNotFoundException(String.format(RmMessages.PRODUCT_NOT_FOUND, recipeProduct.productId()));
        }

        return new RecipeProduct(
                0L,
                productById.get(),
                recipe,
                recipeProduct.quantity()
        );
    }
}