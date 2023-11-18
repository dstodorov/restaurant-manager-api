package com.dstod.restaurantmanagerapi.users.initializers;

import com.dstod.restaurantmanagerapi.users.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInit implements CommandLineRunner {
    private final RoleService roleService;

    public RoleInit(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void run(String... args){
        if (roleService.isUserRolesTableEmpty()) {
            roleService.loadUserRoles();
        }
    }
}
