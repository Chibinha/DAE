package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllMakers",
                query = "SELECT m FROM Manufacturer m ORDER BY m.name" // JPQL
        )
})
public class Manufacturer extends User {
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.REMOVE)
    private List<Product> products;
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.REMOVE)
    private List<InventoryItem> inventoryItems;

    public Manufacturer() {
        this.products = new ArrayList<>();
        this.inventoryItems = new ArrayList<>();
    }

    public Manufacturer(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.products = new ArrayList<Product>();
        this.inventoryItems = new ArrayList<InventoryItem>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
