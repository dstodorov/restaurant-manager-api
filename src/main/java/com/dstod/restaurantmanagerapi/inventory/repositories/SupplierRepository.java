package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);

    Optional<Supplier> findByPhoneNumber(String phoneNumber);

    Optional<Supplier> findByName(String name);

    @Query("SELECT s FROM Supplier s WHERE (s.name = :name AND s.id <> :supplierId)")
    Optional<Supplier> findByNameExcludingSupplierId(long supplierId, String name);
    @Query("SELECT s FROM Supplier s WHERE (s.email = :email AND s.id <> :supplierId)")
    Optional<Supplier> findByEmailExcludingSupplierId(long supplierId, String email);
    @Query("SELECT s FROM Supplier s WHERE (s.phoneNumber = :phoneNumber AND s.id <> :supplierId)")
    Optional<Supplier> findByPhoneNumberExcludingSupplierId(long supplierId, String phoneNumber);
}
