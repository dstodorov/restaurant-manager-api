package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDto;
import com.dstod.restaurantmanagerapi.inventory.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Manage product APIs")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create new product")
    @PostMapping
    public ResponseEntity<SuccessResponse> createProduct(@RequestBody @Valid ProductDto product) {
        return ResponseEntity.ok(this.productService.createProduct(product));
    }

    @Operation(summary = "Get product info by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.productService.getProduct(id));
    }

    @Operation(summary = "Get list of all products")
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return this.productService
                .getAllProducts()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .build());
    }

    @Operation(summary = "Update product by given ID")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.ok(this.productService.updateProduct(id, productDto));
    }
}