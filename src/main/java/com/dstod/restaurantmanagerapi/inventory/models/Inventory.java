package com.dstod.restaurantmanagerapi.inventory.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "current_quantity")
    private Double currentQuantity;

    @Column(nullable = false, name = "order_quantity")
    private Double orderQuantity;

    @Column(nullable = false, name = "batch_price")
    private BigDecimal batchPrice;

    @Column(nullable = false, name = "order_date")
    private LocalDate orderDate;

    @Column(nullable = false, name = "expiry_date")
    private LocalDate expiryDate;

    @Basic
    private Boolean wasted;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public Inventory() {
    }

    public Inventory(long id, Double currentQuantity, Double orderQuantity, BigDecimal batchPrice, LocalDate orderDate, LocalDate expiryDate, Boolean wasted, Product product, Supplier supplier) {
        this.id = id;
        this.currentQuantity = currentQuantity;
        this.orderQuantity = orderQuantity;
        this.batchPrice = batchPrice;
        this.orderDate = orderDate;
        this.expiryDate = expiryDate;
        this.wasted = wasted;
        this.product = product;
        this.supplier = supplier;
    }

    public long getId() {
        return id;
    }

    public Inventory setId(long id) {
        this.id = id;
        return this;
    }

    public Double getCurrentQuantity() {
        return currentQuantity;
    }

    public Inventory setCurrentQuantity(Double currentQuantity) {
        this.currentQuantity = currentQuantity;
        return this;
    }

    public Double getOrderQuantity() {
        return orderQuantity;
    }

    public Inventory setOrderQuantity(Double orderQuantity) {
        this.orderQuantity = orderQuantity;
        return this;
    }

    public BigDecimal getBatchPrice() {
        return batchPrice;
    }

    public Inventory setBatchPrice(BigDecimal batchPrice) {
        this.batchPrice = batchPrice;
        return this;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Inventory setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
        return this;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public Inventory setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public Boolean getWasted() {
        return wasted;
    }

    public Inventory setWasted(Boolean wasted) {
        this.wasted = wasted;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Inventory setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public Inventory setSupplier(Supplier supplier) {
        this.supplier = supplier;
        return this;
    }
}
