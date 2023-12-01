package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.*;
import com.dstod.restaurantmanagerapi.inventory.models.entities.*;
import com.dstod.restaurantmanagerapi.inventory.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

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

    public SuccessResponse addToInventory(AddInventoryProductDto inventoryProductDto) {
        Supplier supplier = ensureSupplierExist(inventoryProductDto);

        Product product = ensureProductExist(inventoryProductDto);

        Inventory inventoryProduct = mapToInventoryProduct(inventoryProductDto, product, supplier);

        Inventory savedInventoryProduct = this.inventoryRepository.save(inventoryProduct);

        InventoryProductInfoDto savedInventoryProductInfoDto = map(savedInventoryProduct);

        return new SuccessResponse(ApplicationMessages.INVENTORY_PRODUCT_CREATED, new Date(), savedInventoryProductInfoDto);
    }

    public InventoryProductInfoDto getInventoryProductInfo(Long id) {
        return this.inventoryRepository
                .findById(id)
                .map(this::map)
                .orElseThrow(() -> new InventoryProductNotFoundException(String.format(ApplicationMessages.INVENTORY_PRODUCT_MISSING, id)));
    }

    public Optional<List<InventoryProductInfoDto>> getInventoryProducts() {
        return Optional.of(this.inventoryRepository
                .findAll()
                .stream()
                .map(this::map)
                .toList());
    }

    public SuccessResponse checkoutRecipeProducts(long recipeId, Integer numberOfDishes) {
        Recipe recipe = getRecipeById(recipeId);

        ArrayList<String> inventoryIssues = new ArrayList<>();

        for (RecipeProduct recipeProduct : recipeProductRepository.findRecipeProductByRecipe(recipe)) {

            String productAvailabilityIssue = checkProductAvailability(recipeProduct, numberOfDishes);

            if (!productAvailabilityIssue.isEmpty()) {
                inventoryIssues.add(productAvailabilityIssue);
            }
        }

        if (!inventoryIssues.isEmpty()) {
            throw new InventoryStockIssuesException("There are inventory stock issues", inventoryIssues);
        }

        for (RecipeProduct recipeProduct : recipeProductRepository.findRecipeProductByRecipe(recipe)) {
            reduceProductQuantity(recipeProduct, numberOfDishes);
        }

        InventoryCheckoutResponse inventoryCheckoutResponse = getInventoryCheckoutResponse(recipe, numberOfDishes);

        return new SuccessResponse("Successful products checkout", new Date(), inventoryCheckoutResponse);
    }

    private InventoryCheckoutResponse getInventoryCheckoutResponse(Recipe recipe, int numberOfDishes) {
        List<RecipeProduct> recipeProducts = this.recipeProductRepository.findRecipeProductByRecipe(recipe);
        List<CheckoutProduct> checkoutProducts = new ArrayList<>();
        recipeProducts.forEach(p -> {
            checkoutProducts.add(new CheckoutProduct(p.getProduct().getName(), numberOfDishes * p.getQuantity()));
        });

        return new InventoryCheckoutResponse(checkoutProducts);
    }

    private Recipe getRecipeById(long recipeId) {
        Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);

        if (recipeById.isEmpty()) {
            throw new RecipeNotFoundException(String.format(ApplicationMessages.RECIPE_NOT_FOUND, recipeId));
        }

        return recipeById.get();
    }

    private Inventory mapToInventoryProduct(AddInventoryProductDto inventoryProductDto, Product product, Supplier supplier) {
        return new Inventory(
                0,
                inventoryProductDto.orderQuantity(),
                inventoryProductDto.orderQuantity(),
                inventoryProductDto.batchPrice(),
                LocalDate.now(),
                inventoryProductDto.expiryDate(),
                false,
                product,
                supplier
        );
    }

    private Product ensureProductExist(AddInventoryProductDto inventoryProductDto) {
        return productRepository
                .findById(inventoryProductDto.productId())
                .orElseThrow(() -> new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, inventoryProductDto.productId())));
    }

    private Supplier ensureSupplierExist(AddInventoryProductDto inventoryProductDTO) {
        return this.supplierRepository
                .findById(inventoryProductDTO.supplierId())
                .orElseThrow(() -> new SupplierNotFoundException(inventoryProductDTO.supplierId().toString()));
    }

    private InventoryProductInfoDto map(Inventory inventoryProduct) {
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

        return new InventoryProductInfoDto(
                inventoryProduct.getId(),
                Double.parseDouble(String.format("%.2f", inventoryProduct.getCurrentQuantity())),
                supplierInfoDTO,
                productDTO,
                inventoryProduct.getBatchPrice(),
                inventoryProduct.getExpiryDate(),
                inventoryProduct.getWasted()
        );
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
            return String.format(ApplicationMessages.INVENTORY_PRODUCT_MISSING, product.getId());
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
}
