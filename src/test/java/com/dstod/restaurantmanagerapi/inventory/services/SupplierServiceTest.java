package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.inventory.exceptions.DuplicatedSupplierException;
import com.dstod.restaurantmanagerapi.inventory.exceptions.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.Inventory;
import com.dstod.restaurantmanagerapi.inventory.models.Product;
import com.dstod.restaurantmanagerapi.inventory.models.Supplier;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.InventoryProductsDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDTO;
import com.dstod.restaurantmanagerapi.inventory.repositories.SupplierRepository;
import com.dstod.restaurantmanagerapi.inventory.utilities.InventoryUtility;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class SupplierServiceTest {

    private final Long validSupplierId = 1L;
    private final Long invalidSupplierId = -1L;

    @Mock
    SupplierRepository supplierRepository;

    private InOrder inOrder;

    @InjectMocks
    SupplierService supplierService;

    @BeforeEach
    public void setup() {
        inOrder = inOrder(supplierRepository);
    }


    @Test
    @DisplayName("Successful create of supplier")
    public void createSupplierIsSuccessful() {
        // Arrange

        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();
        Supplier supplier = InventoryUtility.createSupplier();

        when(supplierRepository.findByName(supplierDTO.name())).thenReturn(Optional.empty());
        when(supplierRepository.findByEmail(supplierDTO.email())).thenReturn(Optional.empty());
        when(supplierRepository.findByPhoneNumber(supplierDTO.phoneNumber())).thenReturn(Optional.empty());
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        // Act

        Long supplierId = this.supplierService.createSupplier(supplierDTO);

        // Assert

        assertNotNull(supplierId);
        assertEquals(validSupplierId, supplierId);

        // Verify

        inOrder.verify(supplierRepository, times(1)).findByName(supplierDTO.name());
        inOrder.verify(supplierRepository, times(1)).findByEmail(supplierDTO.email());
        inOrder.verify(supplierRepository, times(1)).findByPhoneNumber(supplierDTO.phoneNumber());
        inOrder.verify(supplierRepository, times(1)).save(any(Supplier.class));
    }

    @Test
    @DisplayName("Create supplier - name duplication")
    public void createSupplierNameDuplicationShouldFail() {

        // Arrange
        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();
        Supplier supplier = InventoryUtility.createSupplier();

        when(supplierRepository.findByName(supplierDTO.name())).thenReturn(Optional.of(supplier));

        // Act

        Long supplierId = supplierService.createSupplier(supplierDTO);

        // Assert

        assertNotNull(supplierId);
        assertEquals(invalidSupplierId, supplierId);

        // verify

        inOrder.verify(supplierRepository, times(1)).findByName(supplierDTO.name());
        inOrder.verify(supplierRepository, never()).save(any(Supplier.class));

    }

    @Test
    @DisplayName("Create supplier - email duplication")
    public void createSupplierEmailDuplicationShouldFail() {
        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();
        Supplier supplier = InventoryUtility.createSupplier();

        when(supplierRepository.findByEmail(supplierDTO.email())).thenReturn(Optional.of(supplier));

        // Act

        Long supplierId = supplierService.createSupplier(supplierDTO);

        // Assert

        assertNotNull(supplierId);
        assertEquals(invalidSupplierId, supplierId);

        // verify

        inOrder.verify(supplierRepository, times(1)).findByEmail(supplierDTO.email());
        inOrder.verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    @DisplayName("Create supplier - phone number duplication")
    public void createSupplierPhoneNumberDuplicationShouldFail() {
        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();
        Supplier supplier = InventoryUtility.createSupplier();

        when(supplierRepository.findByPhoneNumber(supplierDTO.phoneNumber())).thenReturn(Optional.of(supplier));

        // Act

        Long supplierId = supplierService.createSupplier(supplierDTO);

        // Assert

        assertNotNull(supplierId);
        assertEquals(invalidSupplierId, supplierId);

        // verify

        inOrder.verify(supplierRepository, times(1)).findByPhoneNumber(supplierDTO.phoneNumber());
        inOrder.verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    @DisplayName("Get supplier info return supplier")
    public void getSupplierInfoReturnSupplier() {
        // Arrange
        Supplier supplier = InventoryUtility.createSupplier();

        when(supplierRepository.findById(validSupplierId)).thenReturn(Optional.of(supplier));

        // Act
        Optional<SupplierDTO> supplierDTO = supplierService.getSupplier(validSupplierId);

        // Assert
        assertNotNull(supplierDTO);
        assertEquals(validSupplierId, supplierDTO.get().id());

        // Verify
        inOrder.verify(supplierRepository, times(1)).findById(validSupplierId);
    }

    @Test
    @DisplayName("Update supplier info - Successful")
    public void updateSupplierInfoSuccessful() {
        // Arrange
        Supplier supplier = InventoryUtility.createSupplier();
        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();

        when(supplierRepository.findById(validSupplierId)).thenReturn(Optional.of(supplier));
        when(supplierRepository.findByNameOrEmailOrPhoneNumber(validSupplierId, supplierDTO.name(), supplierDTO.email(), supplierDTO.phoneNumber())).thenReturn(Optional.empty());
        when(supplierRepository.save(any(Supplier.class))).thenReturn(supplier);

        // Act
        SupplierDTO supplierDTOResponse = this.supplierService.updateSupplier(validSupplierId, supplierDTO);

        // Assert
        assertNotNull(supplierDTOResponse);
        assertEquals(supplierDTO.id(), validSupplierId);
        assertEquals(supplierDTO.name(), supplierDTOResponse.name());
        assertEquals(supplierDTO.phoneNumber(), supplierDTOResponse.phoneNumber());
        assertEquals(supplierDTO.active(), supplierDTOResponse.active());

        // Verify
        inOrder.verify(supplierRepository, times(1)).findById(validSupplierId);
        inOrder.verify(supplierRepository, times(1)).findByNameOrEmailOrPhoneNumber(validSupplierId, supplierDTO.name(), supplierDTO.email(), supplierDTO.phoneNumber());
        inOrder.verify(supplierRepository, times(1)).save(supplier);
    }

    @Test
    @DisplayName("Update not existing supplier return exception")
    public void updateNotExistentProductReturnException() {
        // Arrange
        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();

        when(supplierRepository.findById(validSupplierId)).thenThrow(SupplierNotFoundException.class);

        // Act and Assert
        assertThrows(SupplierNotFoundException.class, () -> {
            supplierService.updateSupplier(validSupplierId, supplierDTO);
        });

        // Verify

        inOrder.verify(supplierRepository, times(1)).findById(validSupplierId);
        inOrder.verify(supplierRepository, never()).findByNameOrEmailOrPhoneNumber(
                validSupplierId,
                supplierDTO.name(),
                supplierDTO.email(),
                supplierDTO.phoneNumber()
        );
        inOrder.verify(supplierRepository, never()).save(any(Supplier.class));
    }
    @Test
    @DisplayName("Update supplier with duplicated details return exception")
    public void updateSupplierWithDuplicatedDetailsReturnException() {

        // Arrange
        SupplierDTO supplierDTO = InventoryUtility.createValidSupplierDto();
        Supplier supplier = InventoryUtility.createSupplier();

        when(supplierRepository.findById(validSupplierId)).thenReturn(Optional.of(supplier));
        when(supplierRepository.findByNameOrEmailOrPhoneNumber(
                validSupplierId,
                supplierDTO.name(),
                supplierDTO.email(),
                supplierDTO.phoneNumber()
        )).thenReturn(Optional.of(supplier));

        // Act and Assert
        assertThrows(DuplicatedSupplierException.class, () -> {
            supplierService.updateSupplier(validSupplierId, supplierDTO);
        });

        // Verify

        inOrder.verify(supplierRepository, times(1)).findById(validSupplierId);
        inOrder.verify(supplierRepository, times(1)).findByNameOrEmailOrPhoneNumber(
                validSupplierId,
                supplierDTO.name(),
                supplierDTO.email(),
                supplierDTO.phoneNumber()
        );
        inOrder.verify(supplierRepository, never()).save(any(Supplier.class));
    }

    @Test
    @DisplayName("Get list of suppliers - Successful")
    public void getListOfSuppliersIsSuccessful() {
        // Arrange
        List<Supplier> suppliers = InventoryUtility.getListOfSuppliers();
        when(supplierRepository.findAll()).thenReturn(suppliers);

        // Act
        Optional<List<SupplierDTO>> allSuppliers = supplierService.getAllSuppliers();

        // Assert
        assertNotNull(allSuppliers.get());
        assertEquals(suppliers.size(), allSuppliers.get().size());

        List<SupplierDTO> supplierDTOS = allSuppliers.get();

        for(int i = 0; i < supplierDTOS.size(); i++) {
            assertEquals(suppliers.get(i).getId(), supplierDTOS.get(i).id());
            assertEquals(suppliers.get(i).getName(), supplierDTOS.get(i).name());
            assertEquals(suppliers.get(i).getEmail(), supplierDTOS.get(i).email());
            assertEquals(suppliers.get(i).getPhoneNumber(), supplierDTOS.get(i).phoneNumber());
            assertEquals(suppliers.get(i).getDescription(), supplierDTOS.get(i).description());
            assertEquals(suppliers.get(i).getActive(), supplierDTOS.get(i).active());
        }

        // Verify
        inOrder.verify(supplierRepository, times(1)).findAll();
    }
}
