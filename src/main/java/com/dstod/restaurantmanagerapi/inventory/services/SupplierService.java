package com.dstod.restaurantmanagerapi.inventory.services;

import com.dstod.restaurantmanagerapi.inventory.exceptions.DuplicatedSupplierException;
import com.dstod.restaurantmanagerapi.inventory.exceptions.SupplierNotFoundException;
import com.dstod.restaurantmanagerapi.inventory.models.Supplier;
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

        Optional<Supplier> supplierByName = this.supplierRepository.findByName(supplierDTO.name());
        Optional<Supplier> supplierByEmail = this.supplierRepository.findByEmail(supplierDTO.email());
        Optional<Supplier> supplierByPhoneNumber = this.supplierRepository.findByPhoneNumber(supplierDTO.phoneNumber());

        if (supplierByName.isPresent() || supplierByEmail.isPresent() || supplierByPhoneNumber.isPresent()) {
            return -1L;
        }

        Supplier supplier = mapToSupplier(supplierDTO);

        return this.supplierRepository.save(supplier).getId();
    }

    public Optional<SupplierDTO> getSupplier(Long id) {
        return this.supplierRepository.findById(id).map(this::mapToSupplierDTO);
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        // Check if supplier exists, if not, throw exception
        Supplier supplier = this.supplierRepository
                .findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id.toString()));

        Optional<Supplier> supplierByNameAndEmailAndPhoneNumber = this.supplierRepository
                .findByNameAndEmailAndPhoneNumber(id,
                        supplierDTO.name(),
                        supplierDTO.email(),
                        supplierDTO.phoneNumber());

        // Throw duplication exception if there record with same name/email/phoneNumber
        if (supplierByNameAndEmailAndPhoneNumber.isPresent()) {
            throw new DuplicatedSupplierException(id.toString());
        }

        // Saving changes
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

    public void changeStatus() {

    }

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
}
