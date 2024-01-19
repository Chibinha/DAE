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
    @ManyToMany
    @JoinTable(
        name = "packages_products",
        joinColumns = @JoinColumn(name = "product_package_id"),
        inverseJoinColumns = {
            @JoinColumn(name = "productPackage_id"),

        }
    )
    private List<InventoryItem> inventoryItems;

    public ProductPackage() {
        this.inventoryItems = new ArrayList<>();
    }

    public ProductPackage(int packageType, String material) {
        super(packageType, material);
        //this.product = product;
        this.inventoryItems = new ArrayList<>();
    }

    public InventoryItem getCurrentPhysicalProduct() {
        if (!inventoryItems.isEmpty())
            return inventoryItems.get(inventoryItems.size() - 1);
        return null;
    }

    public List<InventoryItem> getPhysicalProducts() {
        return inventoryItems;
    }

    public void setPhysicalProducts(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public void addPhysicalProduct(InventoryItem inventoryItem) {
        if (inventoryItem != null)
            this.inventoryItems.add(inventoryItem);
    }

    public void removePhysicalProduct(InventoryItem inventoryItem) {
        if (inventoryItem != null)
            this.inventoryItems.remove(inventoryItem);
    }
}
