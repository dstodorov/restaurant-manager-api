package com.dstod.restaurantmanagerapi.inventory.models;

import com.dstod.restaurantmanagerapi.inventory.models.enums.ProductCategory;
import com.dstod.restaurantmanagerapi.inventory.models.enums.UnitType;
import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory category;

    @Enumerated(EnumType.STRING)
    private UnitType unit;

    public Product() {
    }

    public Product(Long id, String name, ProductCategory category, UnitType unit) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public Product setCategory(ProductCategory category) {
        this.category = category;
        return this;
    }

    public UnitType getUnit() {
        return unit;
    }

    public Product setUnit(UnitType unit) {
        this.unit = unit;
        return this;
    }
}
