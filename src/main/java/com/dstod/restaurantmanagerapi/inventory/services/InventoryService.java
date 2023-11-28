package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.InventoryProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.*;
import com.dstod.restaurantmanagerapi.inventory.models.entities.*;
import com.dstod.restaurantmanagerapi.inventory.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeProductRepository recipeProductRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, SupplierRepository supplierRepository, RecipeRepository recipeRepository, RecipeProductRepository recipeProductRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.recipeRepository = recipeRepository;
        this.recipeProductRepository = recipeProductRepository;
    }

    public Long addToInventory(AddInventoryProductDTO inventoryProductDTO) {
        Supplier supplier = this.supplierRepository
                .findById(inventoryProductDTO.supplierId())
                .orElseThrow(() -> new SupplierNotFoundException(inventoryProductDTO.supplierId().toString()));

        Product product = productRepository
                .findById(inventoryProductDTO.productId())

                .orElseThrow(() -> new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, inventoryProductDTO.productId())));

        LocalDate orderDate = LocalDate.now();

        Inventory inventoryProduct = new Inventory(
                0,
                inventoryProductDTO.orderQuantity(),
                inventoryProductDTO.orderQuantity(),
                inventoryProductDTO.batchPrice(),
                orderDate,
                inventoryProductDTO.expiryDate(),
                false,
                product,
                supplier
        );

        return this.inventoryRepository.save(inventoryProduct).getId();
    }

    public InventoryProductInfoDTO getInventoryProductInfo(Long id) {
        return this.inventoryRepository
                .findById(id)
                .map(this::map)
                .orElseThrow(() -> new InventoryProductNotFoundException(id.toString()));
    }

    public Optional<List<InventoryProductInfoDTO>> getInventoryProducts() {
        return Optional.of(this.inventoryRepository
                .findAll().stream().map(this::map).toList());
    }

    private InventoryProductInfoDTO map(Inventory inventoryProduct) {
        SupplierInfoDTO supplierInfoDTO = new SupplierInfoDTO(
                inventoryProduct.getSupplier().getId(),
                inventoryProduct.getSupplier().getName(),
                inventoryProduct.getSupplier().getEmail(),
                inventoryProduct.getSupplier().getPhoneNumber()
        );

        ProductDto productDTO = new ProductDto(
                inventoryProduct.getProduct().getId(),
                inventoryProduct.getProduct().getName(),
                inventoryProduct.getProduct().getCategory().name(),
                inventoryProduct.getProduct().getUnit().name()
        );

        return new InventoryProductInfoDTO(
                inventoryProduct.getId(),
                Double.parseDouble(String.format("%.2f", inventoryProduct.getCurrentQuantity())),
                supplierInfoDTO,
                productDTO,
                inventoryProduct.getBatchPrice(),
                inventoryProduct.getExpiryDate(),
                inventoryProduct.getWasted()
        );
    }


    public CheckoutStatus checkoutRecipeProducts(long recipeId, Integer numberOfDishes) {
        Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);

        if (recipeById.isEmpty()) {
            return createCheckoutStatus(false, HttpStatus.NOT_FOUND, String.format(ApplicationMessages.RECIPE_NOT_FOUND, recipeId));
        }

        Recipe recipe = recipeById.get();
        ArrayList<String> inventoryIssues = new ArrayList<>();

        for (RecipeProduct recipeProduct: recipeProductRepository.findRecipeProductByRecipe(recipe)) {
            String productAvailabilityIssue = checkProductAvailability(recipeProduct, numberOfDishes);
            
            if (!productAvailabilityIssue.isEmpty()) {
                inventoryIssues.add(productAvailabilityIssue);
            }
        }

        if (inventoryIssues.isEmpty()) {
            for (RecipeProduct  recipeProduct: recipeProductRepository.findRecipeProductByRecipe(recipe)) {
                reduceProductQuantity(recipeProduct, numberOfDishes);
            }

            return createCheckoutStatus(true, HttpStatus.OK, "");
        }

        return createCheckoutStatus(false, HttpStatus.NOT_MODIFIED, inventoryIssues.toArray(new String[0]));
    }
    
    private void reduceProductQuantity(RecipeProduct recipeProduct, int numberOfDishes) {
        Optional<Inventory> inventoryProduct = this.inventoryRepository.findByProduct(recipeProduct.getProduct());

        if (inventoryProduct.isPresent()) {
            Inventory inventory = inventoryProduct.get();
            inventory.setCurrentQuantity(inventoryProduct.get().getCurrentQuantity() - recipeProduct.getQuantity() * numberOfDishes);
            inventoryRepository.save(inventory);
        }
    }

    private String checkProductAvailability(RecipeProduct recipeProduct, int numberOfDishes) {
        Optional<Inventory> inventoryOptional = this.inventoryRepository.findByProduct(recipeProduct.getProduct());
        Product product = recipeProduct.getProduct();

        if (inventoryOptional.isEmpty()) {

            return String.format(ApplicationMessages.INVENTORY_PRODUCT_MISSING, product.getId(), product.getName());
        }

        Inventory inventory = inventoryOptional.get();

        if (inventory.getWasted()) {
            return String.format(ApplicationMessages.PRODUCT_WASTED, product.getId(), product.getName());
        }

        double requiredQuantity = recipeProduct.getQuantity() * numberOfDishes;

        if (inventory.getCurrentQuantity() < requiredQuantity) {
            return String.format(ApplicationMessages.PRODUCT_OUT_OF_STOCK, product.getId(), product.getName());
        }

        return "";
    }

    private CheckoutStatus createCheckoutStatus(boolean success, HttpStatus status, String... messages) {
        return new CheckoutStatus(success, status, Arrays.asList(messages));
    }
}
