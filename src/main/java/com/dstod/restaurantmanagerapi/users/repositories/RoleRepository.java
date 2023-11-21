package com.dstod.restaurantmanagerapi.users.repositories;

import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(RoleType roleType);
}
