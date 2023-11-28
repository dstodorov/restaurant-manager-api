package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.common.exceptions.inventory.DuplicatedSupplierDetailsException;
import com.dstod.restaurantmanagerapi.common.exceptions.inventory.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.common.messages.ApplicationMessages;
import com.dstod.restaurantmanagerapi.common.models.SuccessResponse;
import com.dstod.restaurantmanagerapi.inventory.models.entities.Supplier;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDto;
import com.dstod.restaurantmanagerapi.inventory.repositories.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SuccessResponse createSupplier(SupplierDto supplierDto) {

        ensureSupplierDetailsDoesNotExist(0, supplierDto.name(), supplierDto.email(), supplierDto.phoneNumber());

        Supplier supplierEntity = mapToSupplier(supplierDto);

        Supplier savedSupplier = this.supplierRepository.save(supplierEntity);

        return new SuccessResponse(ApplicationMessages.SUPPLIER_SUCCESSFULLY_CREATED, new Date(), savedSupplier);

    }

    public SupplierDto getSupplier(Long id) {
        return this.supplierRepository
                .findById(id)
                .map(this::mapToSupplierDto)
                .orElseThrow(() ->
                        new SupplierNotFoundException(String.format(ApplicationMessages.SUPPLIER_NOT_FOUND, id))
                );
    }

    public SuccessResponse updateSupplier(Long id, SupplierDto supplierDto) {
        Supplier supplier = this.supplierRepository
                .findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(String.format(ApplicationMessages.SUPPLIER_NOT_FOUND, id)));

        ensureSupplierDetailsDoesNotExist(id, supplierDto.name(), supplierDto.email(), supplierDto.phoneNumber());

        supplier.setName(supplierDto.name());
        supplier.setEmail(supplierDto.email());
        supplier.setPhoneNumber(supplierDto.phoneNumber());
        supplier.setDescription(supplierDto.description());
        supplier.setActive(supplierDto.active());

        Supplier savedSupplier = this.supplierRepository.save(supplier);

        SupplierDto savedSupplierDto = mapToSupplierDto(savedSupplier);

        return new SuccessResponse(ApplicationMessages.SUPPLIER_SUCCESSFULLY_UPDATED, new Date(), savedSupplierDto);
    }

    public Optional<List<SupplierDto>> getAllSuppliers() {
        return Optional.of(this.supplierRepository.findAll().stream().map(this::mapToSupplierDto).toList());
    }

//    public void changeStatus() {
//
//    }

    private SupplierDto mapToSupplierDto(Supplier supplier) {

        return new SupplierDto(
                supplier.getId(),
                supplier.getName(),
                supplier.getPhoneNumber(),
                supplier.getEmail(),
                supplier.getDescription(),
                supplier.getActive()
        );
    }

    private Supplier mapToSupplier(SupplierDto supplierDTO) {

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
