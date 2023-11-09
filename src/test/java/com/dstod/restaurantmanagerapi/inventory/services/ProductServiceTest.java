package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.inventory.models.Product;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import com.dstod.restaurantmanagerapi.inventory.utilities.InventoryUtility;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    @DisplayName("Create product - successful")
    void createProduct() {
        ProductDTO productDto = InventoryUtility.createValidProductDto();
        Product product = InventoryUtility.createValidProduct();

        when(productRepository.findByName(productDto.name())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Long productId = this.productService.createProduct(productDto);

        assertNotNull(productId);
        assertEquals(product.getId(), productId);
        verify(productRepository, times(1)).findByName(productDto.name());
        verify(productRepository, times(1)).save(any(Product.class));

        this.productRepository.deleteAll();
    }

    @Test
    void getProduct() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void updateProduct() {
    }
}