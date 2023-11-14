package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.inventory.models.Supplier;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDTO;
import com.dstod.restaurantmanagerapi.inventory.repositories.SupplierRepository;
import com.dstod.restaurantmanagerapi.inventory.utilities.InventoryUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}