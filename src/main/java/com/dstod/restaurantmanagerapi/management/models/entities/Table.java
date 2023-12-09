package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

@Entity
@jakarta.persistence.Table(name = "tables")
public class Table {
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

    public Table() {
    }

    public Table(long id, int tableNumber, int capacity, boolean active, Region region) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.active = active;
        this.region = region;
    }

    public long getId() {
        return id;
    }

    public Table setId(long id) {
        this.id = id;
        return this;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public Table setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public Table setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Region getRegion() {
        return region;
    }

    public Table setRegion(Region region) {
        this.region = region;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Table setActive(boolean active) {
        this.active = active;
        return this;
    }
}
