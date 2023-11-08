package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.inventory.models.CheckoutStatus;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.AddInventoryProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.CheckoutProductsRequestDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.InventoryProductInfoDTO;
import com.dstod.restaurantmanagerapi.inventory.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public ResponseEntity<AddInventoryProductDTO> addInventoryProduct(@Valid @RequestBody AddInventoryProductDTO inventoryProductDTO,
                                                                      UriComponentsBuilder uriComponentsBuilder) {

        Long inventoryProductId = this.inventoryService.addToInventory(inventoryProductDTO);

        return ResponseEntity
                .created(uriComponentsBuilder
                        .path("/api/v1/inventory/{id}")
                        .build(inventoryProductId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryProductInfoDTO> getInventoryProductInfo(@PathVariable Long id) {

        InventoryProductInfoDTO inventoryProductInfo = this.inventoryService.getInventoryProductInfo(id);

        return ResponseEntity.ok(inventoryProductInfo);
    }

    @GetMapping
    public ResponseEntity<List<InventoryProductInfoDTO>> getInventoryProducts() {
        return this.inventoryService.getInventoryProducts().map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/checkout")
    public ResponseEntity<CheckoutStatus> checkoutRecipeProducts(@Valid @RequestBody CheckoutProductsRequestDTO request) {
        CheckoutStatus checkoutStatus = this.inventoryService.checkoutRecipeProducts(request.recipeId(), request.quantity());

        if (checkoutStatus.successfulCheckout()) {
            return ResponseEntity.ok(checkoutStatus);
        }

        return ResponseEntity.badRequest().body(checkoutStatus);
    }
}