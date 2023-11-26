package com.dstod.restaurantmanagerapi.inventory.controllers;

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
        return this.productService.getAllProducts().map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Get product info by given ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
        Optional<ProductDTO> product = this.productService.getProduct(id);

        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Create new product")
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody @Valid ProductDTO product, UriComponentsBuilder uriComponentsBuilder) {

        Long productId = this.productService.createProduct(product);

        if (productId == -1) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.created(uriComponentsBuilder.path("/api/v1/products/{id}").build(productId)).build();
    }

    @Operation(summary = "Update product by given ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
        ProductDTO updatedProduct = this.productService.updateProduct(id, productDTO);

        return ResponseEntity.ok(updatedProduct);
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