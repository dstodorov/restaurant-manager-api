package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.core.exceptions.inventory.InventoryProductNotFoundException;
import com.dstod.restaurantmanagerapi.core.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.core.exceptions.inventory.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.CheckoutStatus;
import com.dstod.restaurantmanagerapi.inventory.models.Inventory;
import com.dstod.restaurantmanagerapi.inventory.models.Product;
import com.dstod.restaurantmanagerapi.inventory.models.Supplier;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.*;
import com.dstod.restaurantmanagerapi.inventory.repositories.InventoryRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import com.dstod.restaurantmanagerapi.inventory.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public InventoryService(InventoryRepository inventoryRepository, ProductRepository productRepository, SupplierRepository supplierRepository) {
        this.inventoryRepository = inventoryRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
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



    public CheckoutStatus productsCheckout(Double recipeId, Integer numberOfDishes) {
        // Check quantities
        // Check wasted for products
        // Reduce quantities
        // Return CheckoutStatus

        return null;
    }
}
