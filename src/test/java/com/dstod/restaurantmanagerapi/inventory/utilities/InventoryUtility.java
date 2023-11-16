package com.dstod.restaurantmanagerapi.inventory.utilities;

import com.dstod.restaurantmanagerapi.inventory.models.Product;
import com.dstod.restaurantmanagerapi.inventory.models.Supplier;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.ProductDTO;
import com.dstod.restaurantmanagerapi.inventory.models.dtos.SupplierDTO;
import com.dstod.restaurantmanagerapi.inventory.models.enums.ProductCategory;
import com.dstod.restaurantmanagerapi.inventory.models.enums.UnitType;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtility {
    public static ProductDTO createValidProductDto() {
        return new ProductDTO(
                1L,
                "Banana",
                "FRUIT",
                "KG"
        );
    }

    public static Product createValidProduct() {
        return new Product(
                1L,
                "Banana",
                ProductCategory.FRUIT,
                UnitType.KG
        );
    }

    public static Product createSecondValidProduct() {
        return new Product(
                2L,
                "Orange",
                ProductCategory.FRUIT,
                UnitType.KG
        );
    }

    public static Product createValidProductTwo() {
        return new Product(
                2L,
                "Steak",
                ProductCategory.MEAT,
                UnitType.KG
        );
    }

    public static List<Product> getListOfProducts() {
        Product product1 = createValidProduct();
        Product product2 = createValidProductTwo();

        List<Product> products = new ArrayList<>();

        products.add(product1);
        products.add(product2);

        return products;
    }

    public static SupplierDTO createValidSupplierDto() {
        return new SupplierDTO(
                1L,
                "Meat factory",
                "+359889123456",
                "meat_factory@gmail.com",
                "Supplier for all kinds of meat",
                true
        );
    }

    public static Supplier createSupplier() {
        return new Supplier(
                1L,
                "Meat factory",
                "+359889123456",
                "meat_factory@gmail.com",
                "Supplier for all kinds of meat",
                true
        );
    }

    public static Supplier createSupplierTwo() {
        return new Supplier(
                2L,
                "Vegetable factory",
                "+359889123457",
                "vegetable_factory@gmail.com",
                "Supplier for all kinds of vegetables",
                true
        );
    }

    public static List<Supplier> getListOfSuppliers() {
        Supplier supplier1 = createSupplier();
        Supplier supplier2 = createSupplierTwo();

        List<Supplier> suppliers = new ArrayList<>();

        suppliers.add(supplier1);
        suppliers.add(supplier2);

        return suppliers;
    }
}
