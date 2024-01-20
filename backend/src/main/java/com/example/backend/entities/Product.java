package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.id"
        ),
        @NamedQuery(
                name = "getMakerProducts",
                query = "SELECT p FROM Product p WHERE p.manufacturer.username = :username ORDER BY p.id"
        ),

})
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    private double price;
    private String description;
    private double weight;
    private String ingredients;
    @OneToMany(mappedBy = "product")
    private List<InventoryItem> inventoryItems;
    private long inStock;

    @ManyToOne
    @JoinColumn(name = "maker_id")
    @NotNull
    private Manufacturer manufacturer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    @NotNull
    private Timestamp creationTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_timestamp")
    @NotNull
    private Timestamp updateTimestamp;

    public Product() {
        this.inventoryItems = new ArrayList<>();
    }

    public Product(String name, double price, String description, double weight, String ingredients, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.ingredients = ingredients;
        this.manufacturer = manufacturer;
        this.inventoryItems = new ArrayList<>();
        this.inStock = 0;
        this.creationTimestamp = new Timestamp(System.currentTimeMillis());
        this.updateTimestamp = this.creationTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long product_id) {
        this.id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public Manufacturer getMaker() {
        return manufacturer;
    }

    public void setMaker(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
        for (InventoryItem inventoryItem : inventoryItems) {
            if (inventoryItem.getOrder() == null) {
                this.inStock++;
            }
        }
    }

    public void addInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItems.add(inventoryItem);
        this.inStock++;
    }

    public void removeInventoryItem(InventoryItem inventoryItem) {
        if (inventoryItem.getOrder() == null) {
            this.inStock--;
        }
        this.inventoryItems.remove(inventoryItem);
    }

    public long getInStock() {
        return inStock;
    }

    public void setInStock(long inStock) {
        this.inStock = inStock;
    }

    //add inStock
    public void addToStock() {
        this.inStock ++;
    }

    //remove inStock
    public void removeFromStock() {
        this.inStock --;
    }

    public Timestamp getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Timestamp createdAt) {
        this.creationTimestamp = createdAt;
    }

    public Timestamp getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Timestamp updatedAt) {
        this.updateTimestamp = updatedAt;
    }

}
