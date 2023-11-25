package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.DuplicatedProductException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.ProductNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Product;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.repositories.ProductRepository;
import com.dstod.restaurantmanagerapi.inventory.utilities.InventoryUtility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private static final long invalidProductId = -1L;
    private static final long validProductId = 1L;

    @Mock
    ProductRepository productRepository;

    private InOrder inOrder;

    @InjectMocks
    ProductService productService;

    @BeforeEach
    public void setup() {
         inOrder = inOrder(productRepository);
    }

    @Test
    @DisplayName("Create valid product should be successful")
    void createValidProduct() {

        // Arrange
        ProductDTO productDto = InventoryUtility.createValidProductDto();
        Product product = InventoryUtility.createValidProduct();

        when(productRepository.findByName(productDto.name())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Long productId = this.productService.createProduct(productDto);

        // Assert
        assertNotNull(productId);
        assertEquals(product.getId(), productId);

        // Verify
        inOrder.verify(productRepository, times(1)).findByName(productDto.name());
        inOrder.verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Create existing product shouldn't be successful")
    void createProductWithExistingName() {

        // Arrange
        ProductDTO validButExistentProductDto = InventoryUtility.createValidProductDto();
        Product product = InventoryUtility.createValidProduct();

        when(productRepository.findByName(validButExistentProductDto.name())).thenReturn(Optional.of(product));

        // Act
        Long productId = this.productService.createProduct(validButExistentProductDto);

        // Assert
        assertNotNull(productId);
        assertEquals(invalidProductId, productId);

        // Verify
        inOrder.verify(productRepository, times(1)).findByName(validButExistentProductDto.name());
        inOrder.verify(productRepository, times(0)).save(product);
    }

    @Test
    @DisplayName("Get existent product should be successful")
    void getProductReturnProductInfo() {

        // Arrange
        ProductDTO expectedProduct = InventoryUtility.createValidProductDto();
        Product product = InventoryUtility.createValidProduct();
        when(productRepository.findById(expectedProduct.id())).thenReturn(Optional.of(product));

        // Act
        Optional<ProductDTO> actualProduct = productService.getProduct(expectedProduct.id());

        // Assert
        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct.get());

        // Verify
        inOrder.verify(productRepository, times(1)).findById(expectedProduct.id());
    }

    @Test
    @DisplayName("Get all products should return list of products")
    void getAllProductsShouldReturnListOfProducts() {

        // Arrange
        List<Product> products = InventoryUtility.getListOfProducts();
        when(productRepository.findAll()).thenReturn(products);

        // Act
        Optional<List<ProductDTO>> allProducts = productService.getAllProducts();

        // Assert
        assertNotNull(allProducts.get());
        assertEquals(products.size(), allProducts.get().size());

        List<ProductDTO> productDTOS = allProducts.get();

        for(int i = 0; i < productDTOS.size(); i++) {
            assertEquals(products.get(i).getId(), productDTOS.get(i).id());
            assertEquals(products.get(i).getName(), productDTOS.get(i).name());
            assertEquals(products.get(i).getCategory().name(), productDTOS.get(i).category());
            assertEquals(products.get(i).getUnit().name(), productDTOS.get(i).unit());
        }

        // Verify
        inOrder.verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update product is successful")
    void updateProductIsSuccessful() {
        // Arrange
        ProductDTO productDTO = InventoryUtility.createValidProductDto();
        Product product = InventoryUtility.createValidProduct();


        when(productRepository.findById(validProductId)).thenReturn(Optional.of(product));
        when(productRepository.findByName(productDTO.name())).thenReturn(Optional.empty());
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act

        ProductDTO updatedProductDTO = productService.updateProduct(validProductId, productDTO);

        // Assert

        assertNotNull(updatedProductDTO);
        assertEquals(productDTO.id(), updatedProductDTO.id());
        assertEquals(productDTO.name(), updatedProductDTO.name());
        assertEquals(productDTO.category(), updatedProductDTO.category());
        assertEquals(productDTO.unit(), updatedProductDTO.unit());

        // Verify

        inOrder.verify(productRepository, times(1)).findById(validProductId);
        inOrder.verify(productRepository, times(1)).findByName(productDTO.name());
        inOrder.verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Update non existing product return exception")
    void updateNotExisingProductReturnException() {
        // Arrange

        ProductDTO productDTO = InventoryUtility.createValidProductDto();

        when(productRepository.findById(validProductId)).thenThrow(ProductNotFoundException.class);

        // Act && Assert

        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(validProductId, productDTO));

        // Verify

        inOrder.verify(productRepository, times(1)).findById(validProductId);
        inOrder.verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    @DisplayName("Update product duplicated name return exception")
    void updateProductWithDuplicatedNameReturnException() {
        // Arrange

        ProductDTO updatedProductDto = InventoryUtility.createValidProductDto();

        Product existingProduct = InventoryUtility.createValidProduct();
        Product anotherProduct = InventoryUtility.createSecondValidProduct();

        when(productRepository.findById(existingProduct.getId())).thenReturn(Optional.of(existingProduct));
        when(productRepository.findByName(updatedProductDto.name())).thenReturn(Optional.of(anotherProduct));

        // Act and Assert

        assertThrows(DuplicatedProductException.class, () -> productService.updateProduct(existingProduct.getId(), updatedProductDto));

        // Verify

        verify(productRepository, times(1)).findById(existingProduct.getId());
        verify(productRepository, times(1)).findByName(updatedProductDto.name());
        verify(productRepository, never()).save(any(Product.class));
    }

}