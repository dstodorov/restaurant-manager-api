package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.DuplicatedSupplierDetailsException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Supplier;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDTO;
import com.dstod.restaurantmanagerapi.inventory.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Long createSupplier(SupplierDTO supplierDTO) {

        ensureSupplierDetailsDoesNotExist(0, supplierDTO.name(), supplierDTO.email(), supplierDTO.phoneNumber());

        Supplier supplier = mapToSupplier(supplierDTO);

        return this.supplierRepository.save(supplier).getId();
    }

    public Optional<SupplierDTO> getSupplier(Long id) {
        return this.supplierRepository.findById(id).map(this::mapToSupplierDTO);
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        Supplier supplier = this.supplierRepository
                .findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(String.format(ApplicationMessages.SUPPLIER_NOT_FOUND, id)));

        ensureSupplierDetailsDoesNotExist(id, supplierDTO.name(), supplierDTO.email(), supplierDTO.phoneNumber());

        supplier.setName(supplierDTO.name());
        supplier.setEmail(supplierDTO.email());
        supplier.setPhoneNumber(supplierDTO.phoneNumber());
        supplier.setDescription(supplierDTO.description());
        supplier.setActive(supplierDTO.active());

        this.supplierRepository.save(supplier);

        return new SupplierDTO(
                id,
                supplierDTO.name(),
                supplierDTO.phoneNumber(),
                supplierDTO.email(),
                supplierDTO.description(),
                supplier.getActive()
        );
    }

    public Optional<List<SupplierDTO>> getAllSuppliers() {
        return Optional.of(this.supplierRepository.findAll().stream().map(this::mapToSupplierDTO).toList());
    }

//    public void changeStatus() {
//
//    }

    private SupplierDTO mapToSupplierDTO(Supplier supplier) {

        return new SupplierDTO(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhoneNumber(),
                supplier.getEmail(),
                supplier.getDescription(),
                supplier.getActive()
        );
    }

    private Supplier mapToSupplier(SupplierDTO supplierDTO) {

        return new Supplier(
                0L,
                supplierDTO.name(),
                supplierDTO.phoneNumber(),
                supplierDTO.email(),
                supplierDTO.description(),
                supplierDTO.active()
        );
    }

    private void ensureSupplierDetailsDoesNotExist(long supplierId, String name, String email, String phoneNumber) {
        Optional<Supplier> supplierByName = supplierId > 0 ?
                this.supplierRepository.findByNameExcludingSupplierId(supplierId, name)
                : this.supplierRepository.findByName(name);
        Optional<Supplier> supplierByEmail = supplierId > 0 ?
                this.supplierRepository.findByEmailExcludingSupplierId(supplierId, email)
                : this.supplierRepository.findByEmail(email);
        Optional<Supplier> supplierByPhoneNumber = supplierId > 0 ?
                this.supplierRepository.findByPhoneNumberExcludingSupplierId(supplierId, phoneNumber)
                : this.supplierRepository.findByPhoneNumber(phoneNumber);

        if (supplierByName.isPresent()) {
            throw new DuplicatedSupplierDetailsException(String.format(ApplicationMessages.SUPPLIER_NAME_EXISTS, name));
        }

        if (supplierByEmail.isPresent()) {
            throw new DuplicatedSupplierDetailsException(String.format(ApplicationMessages.SUPPLIER_EMAIL_EXISTS, email));
        }

        if (supplierByPhoneNumber.isPresent()) {
            throw new DuplicatedSupplierDetailsException(String.format(ApplicationMessages.SUPPLIER_PHONE_NUMBER_EXISTS, phoneNumber));
        }
    }
}
