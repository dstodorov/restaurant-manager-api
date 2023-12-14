package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "section_name", nullable = false)
    private String sectionName;

    private boolean active = false;

    @OneToMany(mappedBy = "section", fetch = FetchType.EAGER)
    private List<RTable> RTables = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "floor_id")
    private Floor floor;

    public Section() {
    }

    public Section(long id, String sectionName, boolean active, List<RTable> RTables, Floor floor) {
        this.id = id;
        this.sectionName = sectionName;
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

    public String getSectionName() {
        return sectionName;
    }

    public Section setSectionName(String regionName) {
        this.sectionName = regionName;
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

    public List<RTable> getRTables() {
        return RTables;
    }

    public Section setRTables(List<RTable> RTables) {
        this.RTables = RTables;
        return this;
    }
}
