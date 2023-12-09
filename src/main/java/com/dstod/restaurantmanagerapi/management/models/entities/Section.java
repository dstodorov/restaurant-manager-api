package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@jakarta.persistence.Table(name = "regions")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    private boolean active = false;

    @OneToMany(mappedBy = "id")
    private List<RTable> RTables = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "floor_plan_id")
    private Floor floor;

    public Section() {
    }

    public Section(long id, String regionName, boolean active, List<RTable> RTables, Floor floor) {
        this.id = id;
        this.regionName = regionName;
        this.active = active;
        this.RTables = RTables;
        this.floor = floor;
    }

    public long getId() {
        return id;
    }

    public Section setId(long id) {
        this.id = id;
        return this;
    }

    public List<RTable> getTables() {
        return RTables;
    }

    public Section setTables(List<RTable> RTables) {
        this.RTables = RTables;
        return this;
    }

    public Floor getFloorPlan() {
        return floor;
    }

    public Section setFloorPlan(Floor floor) {
        this.floor = floor;
        return this;
    }

    public String getRegionName() {
        return regionName;
    }

    public Section setRegionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public Section setActive(boolean active) {
        this.active = active;
        return this;
    }

    public Floor getFloor() {
        return floor;
    }

    public Section setFloor(Floor floor) {
        this.floor = floor;
        return this;
    }
}
