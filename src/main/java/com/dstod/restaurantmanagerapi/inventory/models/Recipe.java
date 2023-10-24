package com.dstod.restaurantmanagerapi.inventory.models;

import com.dstod.restaurantmanagerapi.inventory.models.enums.RecipeCategory;
import jakarta.persistence.*;

@Entity
@Table(name = "recipes")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private RecipeCategory category;

    public Recipe() {
    }

    public Recipe(Long id, String name, RecipeCategory category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public Recipe setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Recipe setName(String name) {
        this.name = name;
        return this;
    }

    public RecipeCategory getCategory() {
        return category;
    }

    public Recipe setCategory(RecipeCategory category) {
        this.category = category;
        return this;
    }
}
