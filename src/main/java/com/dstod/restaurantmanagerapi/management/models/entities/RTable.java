package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tables")
public class RTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "table_number")
    private int tableNumber;
    private int capacity;

    private boolean active = false;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Section section;

    public RTable() {
    }

    public RTable(long id, int tableNumber, int capacity, boolean active, Section section) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.active = active;
        this.section = section;
    }

    public long getId() {
        return id;
    }

    public RTable setId(long id) {
        this.id = id;
        return this;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public RTable setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public RTable setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public Section getRegion() {
        return section;
    }

    public RTable setRegion(Section section) {
        this.section = section;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public RTable setActive(boolean active) {
        this.active = active;
        return this;
    }
}
