package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDto;
import com.dstod.restaurantmanagerapi.inventory.services.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@Tag(name = "Manager supplier APIs")
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(summary = "Create new supplier")
    @PostMapping
    public ResponseEntity<SuccessResponse> createSupplier(@RequestBody @Valid SupplierDto supplierDTO) {
        return ResponseEntity.ok(this.supplierService.createSupplier(supplierDTO));
    }

    @Operation(summary = "Get supplier by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Long id) {
        return ResponseEntity.ok(this.supplierService.getSupplier(id));
    }

    @Operation(summary = "Get list of all suppliers")
    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        return this.supplierService
                .getAllSuppliers()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Update supplier info by given ID")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateSupplierInfo(@PathVariable Long id, @RequestBody @Valid SupplierDto supplierDTO) {
        return ResponseEntity.ok(this.supplierService.updateSupplier(id, supplierDTO));
    }
}
