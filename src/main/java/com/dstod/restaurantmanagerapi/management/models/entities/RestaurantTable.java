package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "rm_tables")
public class RestaurantTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "table_number")
    private int tableNumber;
    private int capacity;

    private boolean active = false;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    public RestaurantTable() {
    }

    public RestaurantTable(long id, int tableNumber, int capacity, boolean active, Region region) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.active = active;
        this.region = region;
    }

    public long getId() {
        return id;
    }

    public RestaurantTable setId(long id) {
        this.id = id;
        return this;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public RestaurantTable setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public RestaurantTable setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Region getRegion() {
        return region;
    }

    public RestaurantTable setRegion(Region region) {
        this.region = region;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public RestaurantTable setActive(boolean active) {
        this.active = active;
        return this;
    }
}
