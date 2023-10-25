package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.inventory.models.dtos.AddInventoryProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.InventoryProductInfoDTO;
import com.dstod.restaurantmanagerapi.inventory.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
}