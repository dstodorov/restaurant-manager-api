package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.*;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDto;
import com.dstod.restaurantmanagerapi.inventory.models.enums.ProductCategory;
import com.dstod.restaurantmanagerapi.inventory.models.enums.UnitType;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
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

    public SuccessResponse createProduct(ProductDto productDto) {

        ensureProductDetailsDoesNotExist(productDto.name());

        validateProductData(productDto);

        Product productEntity = mapToProduct(productDto);

        Product savedProduct = this.productRepository.save(productEntity);

        ProductDto savedProductDto = mapToProductDTO(savedProduct);

        return new SuccessResponse(ApplicationMessages.PRODUCT_SUCCESSFULLY_CREATED, new Date(), savedProductDto);
    }

    public ProductDto getProduct(Long id) {
        return this.productRepository
                .findById(id)
                .map(this::mapToProductDTO)
                .orElseThrow(() ->
                        new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, id))
                );
    }

    public Optional<List<ProductDto>> getAllProducts() {
        return Optional.of(this.productRepository.findAll().stream().map(this::mapToProductDTO).toList());
    }

    public SuccessResponse updateProduct(Long id, ProductDto productDTO) {

        Product product = this.productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException(String.format(ApplicationMessages.PRODUCT_NOT_FOUND, id)));

        if (productRepository.findByName(productDTO.name()).filter(p -> !p.getId().equals(id)).isPresent()) {
            throw new MismatchedObjectIdException(String.format(ApplicationMessages.MISMATCHED_OBJECT_EXCEPTION, id));
        }
//
        product.setName(productDTO.name());
        product.setCategory(ProductCategory.valueOf(productDTO.category().toUpperCase()));
        product.setUnit(UnitType.valueOf(productDTO.unit().toUpperCase()));

        Product savedProduct = this.productRepository.save(product);

        ProductDto savedProductDto = mapToProductDTO(savedProduct);

        return new SuccessResponse(ApplicationMessages.PRODUCT_SUCCESSFULLY_UPDATED, new Date(), savedProductDto);
    }

    private ProductDto mapToProductDTO(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getCategory().name(),
                product.getUnit().name()
        );
    }

    private void ensureProductDetailsDoesNotExist(String productName) {
        Optional<Product> productByName = this.productRepository.findByName(productName);

        if (productByName.isPresent()) {
            throw new DuplicatedProductException(String.format(ApplicationMessages.DUPLICATED_PRODUCT, productName));
        }
    }

    private void validateProductData(ProductDto productDto) {

        if (!ProductCategory.isValidCategory(productDto.category())) {
            throw new ProductCategoryNotFoundException(String.format(ApplicationMessages.PRODUCT_WRONG_CATEGORY, productDto.category()));
        }

        if (!UnitType.isValidUnit(productDto.unit())) {
            throw new ProductUnitNotFoundException(String.format(ApplicationMessages.PRODUCT_WRONG_UNITS, productDto.unit()));
        }
    }

    private Product mapToProduct(ProductDto productDto) {
        return new Product(
                0L,
                productDto.name(),
                ProductCategory.valueOf(productDto.category().toUpperCase()),
                UnitType.valueOf(productDto.unit())
        );
    }
}