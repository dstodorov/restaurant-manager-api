package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.core.exceptions.inventory.InventoryProductNotFoundException;
import com.dstod.restaurantmanagerapi.core.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.core.exceptions.inventory.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.*;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.*;
import com.dstod.restaurantmanagerapi.inventory.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
                .orElseThrow(() -> new ProductNotFoundException(inventoryProductDTO.productId().toString()));

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

        ProductDTO productDTO = new ProductDTO(
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
        // Check quantities
        // Check wasted for products
        // Reduce quantities
        // Return CheckoutStatus

        Optional<Recipe> recipeById = this.recipeRepository.findById(recipeId);
        ArrayList<String> messages = new ArrayList<>();

        // If recipe not exist return unsuccessful status
        if (recipeById.isEmpty()) {
            messages.add(String.format("Recipe with id %d, not found!", recipeId));

            return new CheckoutStatus(
                    false,
                    HttpStatus.NOT_FOUND,
                    messages
            );
        }

        // Retrieve all needed products for current recipe
        List<RecipeProduct> recipeProducts = this.recipeProductRepository.findRecipeProductByRecipe(recipeById.get());

        recipeProducts.forEach(recipeProduct -> {
            Optional<Inventory> inventoryProduct = this.inventoryRepository.findByProduct(recipeProduct.getProduct());

            if (inventoryProduct.isEmpty()) {
                messages.add(String.format("Product with %d is missing in the inventory!", recipeProduct.getProduct().getId()));
            }

            if (inventoryProduct.isPresent()) {
                if (inventoryProduct.get().getWasted()) {
                    messages.add(String.format("Product with id %d (%s) is wasted", recipeProduct.getProduct().getId(), recipeProduct.getProduct().getName()));
                }
                if (inventoryProduct.get().getCurrentQuantity() < recipeProduct.getQuantity() * numberOfDishes) {
                    messages.add(String.format("Product with id %d (%s) is out of stock", recipeProduct.getProduct().getId(), recipeProduct.getProduct().getName()));
                }

                if (messages.isEmpty()) {
                    inventoryProduct.get().setCurrentQuantity(inventoryProduct.get().getCurrentQuantity() - recipeProduct.getQuantity() * numberOfDishes);
                    inventoryRepository.save(inventoryProduct.get());
                }
            }
        });

        if (messages.isEmpty()) {
            return new CheckoutStatus(true, HttpStatus.OK, messages);
        }

        return new CheckoutStatus(false, HttpStatus.NOT_MODIFIED, messages);
    }
}
