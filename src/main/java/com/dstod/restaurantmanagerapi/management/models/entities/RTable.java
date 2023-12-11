package com.dstod.restaurantmanagerapi.management.models.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "tables")
public class RTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "table_number")
    private Long tableNumber;
    @Column(nullable = false)
    private Integer capacity;
    private boolean deleted = false;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    public RTable() {
    }

    public RTable(long id, long tableNumber, int capacity, boolean deleted, Section section) {
        this.id = id;
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.deleted = deleted;
        this.section = section;
    }

    public long getId() {
        return id;
    }

    public RTable setId(long id) {
        this.id = id;
        return this;
    }

    public long getTableNumber() {
        return tableNumber;
    }

    public RTable setTableNumber(long tableNumber) {
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

    public Section getSection() {
        return section;
    }

    public RTable setSection(Section section) {
        this.section = section;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public RTable setDeleted(boolean active) {
        this.deleted = active;
        return this;
    }
}
