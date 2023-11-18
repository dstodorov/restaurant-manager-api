package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.users.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final UserRepository userRepository;

    public EmployeeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser() {

    }

    public void deleteUser() {

    }

    public void updateUser() {

    }
}
