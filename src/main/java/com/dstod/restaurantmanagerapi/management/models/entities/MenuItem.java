package com.dstod.restaurantmanagerapi.management.models.entities;

import com.dstod.restaurantmanagerapi.inventory.models.entities.Recipe;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public MenuItem() {
    }

    public MenuItem(long id, BigDecimal price, Menu menu, Recipe recipe) {
        this.id = id;
        this.price = price;
        this.menu = menu;
        this.recipe = recipe;
    }

    public long getId() {
        return id;
    }

    public MenuItem setId(long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuItem setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuItem setMenu(Menu menu) {
        this.menu = menu;
        return this;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public MenuItem setRecipe(Recipe recipe) {
        this.recipe = recipe;
        return this;
    }
}
