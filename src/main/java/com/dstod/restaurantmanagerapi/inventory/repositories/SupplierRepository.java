package com.dstod.restaurantmanagerapi.inventory.repositories;

import com.dstod.restaurantmanagerapi.inventory.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByEmail(String email);

    Optional<Supplier> findByPhoneNumber(String phoneNumber);

    Optional<Supplier> findByName(String name);

    @Query("SELECT s FROM Supplier s WHERE (s.name = :name OR s.email = :email OR s.phoneNumber = :phoneNumber) AND s.id <> :id")
    Optional<Supplier> findByNameAndEmailAndPhoneNumber(Long id, String name, String email, String phoneNumber);
}
