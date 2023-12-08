package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "floors")
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int floor;

    @Column(name = "floor_name")
    private String floorName;

    @OneToMany(mappedBy = "id")
    private List<Region> regions = new ArrayList<>();

    public Floor() {
    }

    public Floor(long id, int floor, String floorName, List<Region> regions) {
        this.id = id;
        this.floor = floor;
        this.floorName = floorName;
        this.regions = regions;
    }

    public long getId() {
        return id;
    }

    public Floor setId(long id) {
        this.id = id;
        return this;
    }

    public int getFloor() {
        return floor;
    }

    public Floor setFloor(int floor) {
        this.floor = floor;
        return this;
    }

    public String getFloorName() {
        return floorName;
    }

    public Floor setFloorName(String floorName) {
        this.floorName = floorName;
        return this;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public Floor setRegions(List<Region> regions) {
        this.regions = regions;
        return this;
    }
}
