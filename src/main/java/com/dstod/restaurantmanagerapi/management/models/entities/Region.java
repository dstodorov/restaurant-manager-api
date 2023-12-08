package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    private boolean active = false;

    @OneToMany(mappedBy = "id")
    private List<RestaurantTable> tables = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "floor_plan_id")
    private Floor floor;

    public Region() {
    }

    public Region(long id, String regionName, boolean active, List<RestaurantTable> tables, Floor floor) {
        this.id = id;
        this.regionName = regionName;
        this.active = active;
        this.tables = tables;
        this.floor = floor;
    }

    public long getId() {
        return id;
    }

    public Region setId(long id) {
        this.id = id;
        return this;
    }

    public List<RestaurantTable> getTables() {
        return tables;
    }

    public Region setTables(List<RestaurantTable> tables) {
        this.tables = tables;
        return this;
    }

    public Floor getFloorPlan() {
        return floor;
    }

    public Region setFloorPlan(Floor floor) {
        this.floor = floor;
        return this;
    }

    public String getRegionName() {
        return regionName;
    }

    public Region setRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Region setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Floor getFloor() {
        return floor;
    }

    public Region setFloor(Floor floor) {
        this.floor = floor;
        return this;
    }
}
