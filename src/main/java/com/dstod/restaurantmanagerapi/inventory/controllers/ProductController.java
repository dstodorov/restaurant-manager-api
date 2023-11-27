package com.dstod.restaurantmanagerapi.inventory.controllers;

import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Manage products APIs")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get list of all products")
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return this.productService
                .getAllProducts()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get product info by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.productService.getProduct(id));
    }

    @Operation(summary = "Create new product")
    @PostMapping
    public ResponseEntity<SuccessResponse> createProduct(@RequestBody @Valid ProductDTO product) {
        SuccessResponse response = this.productService.createProduct(product);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update product by given ID")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
        SuccessResponse response = this.productService.updateProduct(id, productDTO);

        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        if (ex.getAllErrors().get(0).toString().contains("unit")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getFieldValue("unit").toString() + " is not a valid unit type!");
        }

        if (ex.getAllErrors().get(0).toString().contains("category")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getFieldValue("category").toString() + " is not a valid product category!");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}