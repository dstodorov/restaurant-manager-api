package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.core.exceptions.inventory.DuplicatedProductException;
import com.dstod.restaurantmanagerapi.core.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.Product;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.enums.ProductCategory;
import com.dstod.restaurantmanagerapi.inventory.models.enums.UnitType;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long createProduct(ProductDTO newProduct) {

        Optional<Product> productByName = this.productRepository.findByName(newProduct.name());

        if (productByName.isPresent()) {
            return -1L;
        }

        Product product = new Product(
                0L,
                newProduct.name(),
                ProductCategory.valueOf(newProduct.category()),
                UnitType.valueOf(newProduct.unit())
        );


        return this.productRepository.save(product).getId();
    }

    public Optional<ProductDTO> getProduct(Long id) {
        return this.productRepository.findById(id).map(this::mapToProductDTO);
    }

    public Optional<List<ProductDTO>> getAllProducts() {
        return Optional.of(this.productRepository.findAll().stream().map(this::mapToProductDTO).toList());
    }

    private ProductDTO mapToProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getCategory().name(),
                product.getUnit().name()
        );
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Check if product is found, if not, throw exception
        this.productRepository
                .findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id.toString()));

        // Check if product with this name exists in database
        if (productRepository.findByName(productDTO.name()).filter(p -> !p.getId().equals(id)).isPresent()) {
            throw new DuplicatedProductException(id.toString());
        }

        // Saving changes

        Product product = new Product(
                id,
                productDTO.name(),
                ProductCategory.valueOf(productDTO.category()),
                UnitType.valueOf(productDTO.unit())
        );

        Product savedProduct = this.productRepository.save(product);

        return new ProductDTO(
                id,
                savedProduct.getName(),
                savedProduct.getCategory().name(),
                savedProduct.getUnit().name()
        );
    }
}