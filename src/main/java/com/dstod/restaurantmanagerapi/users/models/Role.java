package com.dstod.restaurantmanagerapi.users.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public Role() {
    }

    public Role(RoleType role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public Role setId(long id) {
        this.id = id;
        return this;
    }

    public RoleType getRole() {
        return role;
    }

    public Role setRole(RoleType role) {
        this.role = role;
        return this;
    }
}
