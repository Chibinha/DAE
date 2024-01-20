package com.example.backend.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
    @NamedQuery(
        name = "getAllProductPackages",
        query = "SELECT p FROM ProductPackage p ORDER BY p.id" // JPQL
    )
})
public class ProductPackage extends Package implements Serializable {
    @ManyToMany(mappedBy = "productPackages")
    private List<InventoryItem> inventoryItems;

    public ProductPackage() {
        this.inventoryItems = new ArrayList<>();
    }

    public ProductPackage(String type, String material) {
        super(type, material);
        this.inventoryItems = new ArrayList<>();
    }

    public InventoryItem getCurrentInventoryItem() {
        if (!inventoryItems.isEmpty())
            return inventoryItems.get(inventoryItems.size() - 1);
        return null;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public void addInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem != null)
            this.inventoryItems.add(inventoryItem);
    }

    public void removeInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem != null)
            this.inventoryItems.remove(inventoryItem);
    }
}
