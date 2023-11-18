package com.dstod.restaurantmanagerapi.users.repositories;

import com.dstod.restaurantmanagerapi.users.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
