package com.dstod.restaurantmanagerapi.users.repositories;

import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
