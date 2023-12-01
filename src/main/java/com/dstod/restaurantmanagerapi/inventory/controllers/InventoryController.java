package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.AddInventoryProductDto;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.CheckoutProductsRequestDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.InventoryProductInfoDto;
import com.dstod.restaurantmanagerapi.inventory.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Manage Inventory products APIs")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(summary = "Add new product to the inventory")
    @PostMapping
    public ResponseEntity<SuccessResponse> addInventoryProduct(@Valid @RequestBody AddInventoryProductDto inventoryProductDTO) {
        return ResponseEntity.ok(this.inventoryService.addToInventory(inventoryProductDTO));
    }

    @Operation(summary = "Get inventory product information by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryProductInfoDto> getInventoryProductInfo(@PathVariable Long id) {

        InventoryProductInfoDto inventoryProductInfo = this.inventoryService.getInventoryProductInfo(id);

        return ResponseEntity.ok(inventoryProductInfo);
    }

    @Operation(summary = "Get information about all products in the inventory")
    @GetMapping
    public ResponseEntity<List<InventoryProductInfoDto>> getInventoryProducts() {
        return this.inventoryService
                .getInventoryProducts()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Checkout products from the inventory")
    @GetMapping("/checkout")
    public ResponseEntity<SuccessResponse> checkoutRecipeProducts(@Valid @RequestBody CheckoutProductsRequestDTO request) {
        return ResponseEntity.ok().body(this.inventoryService.checkoutRecipeProducts(request.recipeId(), request.quantity()));
    }
}