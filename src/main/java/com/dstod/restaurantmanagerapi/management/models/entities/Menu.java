package com.dstod.restaurantmanagerapi.management.models.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems = new ArrayList<>();

    public Menu() {
    }

    public Menu(long id, List<MenuItem> menuItems) {
        this.id = id;
        this.menuItems = menuItems;
    }
}
