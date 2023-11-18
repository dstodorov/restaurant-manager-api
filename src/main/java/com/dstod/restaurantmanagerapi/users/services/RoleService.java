package com.dstod.restaurantmanagerapi.users.services;

import com.dstod.restaurantmanagerapi.users.models.entities.Role;
import com.dstod.restaurantmanagerapi.users.models.enums.RoleType;
import com.dstod.restaurantmanagerapi.users.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public boolean isUserRolesTableEmpty() {
        return this.roleRepository.count() == 0;
    }

    public void loadUserRoles() {
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            Role role = new Role(roleType);

            this.roleRepository.save(role);
        });
    }
}
