package com.dstod.restaurantmanagerapi.management.models.entities;


import com.dstod.restaurantmanagerapi.management.models.MenuType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "menus")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    MenuType menuType;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL)
    private List<MenuItem> menuItems = new ArrayList<>();

    @Column(nullable = false)
    private Double revision;

    @Column(name = "last_update", nullable = false)
    private Date lastUpdate;

    @Column(name = "update_comments")
    private String update_comments;

    public Menu() {
    }

    public Menu(long id, MenuType menuType, List<MenuItem> menuItems, Double revision, Date lastUpdate, String update_comments) {
        this.id = id;
        this.menuType = menuType;
        this.menuItems = menuItems;
        this.revision = revision;
        this.lastUpdate = lastUpdate;
        this.update_comments = update_comments;
    }

    public long getId() {
        return id;
    }

    public Menu setId(long id) {
        this.id = id;
        return this;
    }

    public MenuType getMenuType() {
        return menuType;
    }

    public Menu setMenuType(MenuType menuType) {
        this.menuType = menuType;
        return this;
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public Menu setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
        return this;
    }

    public Double getRevision() {
        return revision;
    }

    public Menu setRevision(Double revision) {
        this.revision = revision;
        return this;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public Menu setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
        return this;
    }

    public String getUpdate_comments() {
        return update_comments;
    }

    public Menu setUpdate_comments(String update_comments) {
        this.update_comments = update_comments;
        return this;
    }
}
