package com.dstod.restaurantmanagerapi.users.repositories;

import com.dstod.restaurantmanagerapi.users.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.username = :username and u.id != :userId")
    Optional<User> findByUsernameExcludingUserId(String username, long userId);

    @Query("SELECT u FROM User u WHERE u.email = :email and u.id != :userId")
    Optional<User> findByEmailExcludingUserId(String email, long userId);

    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber and u.id != :userId")
    Optional<User> findByPhoneNumberExcludingUserId(String phoneNumber, long userId);
}
