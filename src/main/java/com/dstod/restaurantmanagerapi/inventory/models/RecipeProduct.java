package com.dstod.restaurantmanagerapi.inventory.models;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe_products")
public class RecipeProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Column(nullable = false)
    private Double quantity;

    public RecipeProduct() {
    }

    public RecipeProduct(long id, Product product, Recipe recipe, Double quantity) {
        this.id = id;
        this.product = product;
        this.recipe = recipe;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public RecipeProduct setId(long id) {
        this.id = id;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public RecipeProduct setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeProduct setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }

    public Double getQuantity() {
        return quantity;
    }

    public RecipeProduct setQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }
}