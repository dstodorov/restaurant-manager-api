package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductCategoryNotFoundException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductUnitNotFoundException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.DuplicatedProductException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.enums.ProductCategory;
import com.dstod.restaurantmanagerapi.inventory.models.enums.UnitType;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public SuccessResponse createProduct(ProductDTO newProduct) {

        Optional<Product> productByName = this.productRepository.findByName(newProduct.name());

        if (productByName.isPresent()) {
            throw new DuplicatedProductException(String.format(ApplicationMessages.DUPLICATED_PRODUCT, newProduct.name()));
        }

        if(!ProductCategory.isValidCategory(newProduct.category())) {
            throw new ProductCategoryNotFoundException(String.format(ApplicationMessages.PRODUCT_WRONG_CATEGORY, newProduct.category()));
        }

        if(!UnitType.isValidUnit(newProduct.unit())) {
            throw new ProductUnitNotFoundException(String.format(ApplicationMessages.PRODUCT_WRONG_UNITS, newProduct.unit()));
        }

        Product product = new Product(
                0L,
                newProduct.name(),
                ProductCategory.valueOf(newProduct.category().toUpperCase()),
                UnitType.valueOf(newProduct.unit())
        );

        Product savedProduct = this.productRepository.save(product);

        ProductDTO savedProductDto = mapToProductDTO(savedProduct);

        return new SuccessResponse(ApplicationMessages.PRODUCT_SUCCESSFULLY_ADDED, new Date(), savedProductDto);
    }

    public ProductDTO getProduct(Long id) {
        return this.productRepository
                .findById(id)
                .map(this::mapToProductDTO)
                .orElseThrow(() ->
                        new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, id))
        );
    }

    public Optional<List<ProductDTO>> getAllProducts() {
        return Optional.of(this.productRepository.findAll().stream().map(this::mapToProductDTO).toList());
    }

    public SuccessResponse updateProduct(Long id, ProductDTO productDTO) {

        this.productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, id)));

        if (productRepository.findByName(productDTO.name()).filter(p -> !p.getId().equals(id)).isPresent()) {
            throw new DuplicatedProductException(id.toString());
        }

        Product product = new Product(
                id,
                productDTO.name(),
                ProductCategory.valueOf(productDTO.category()),
                UnitType.valueOf(productDTO.unit())
        );

        Product savedProduct = this.productRepository.save(product);

        ProductDTO savedProductDto = mapToProductDTO(savedProduct);

        return new SuccessResponse("Successfully updated product", new Date(), savedProductDto);
    }

    private ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory().name(),
                product.getUnit().name()
        );
    }
}