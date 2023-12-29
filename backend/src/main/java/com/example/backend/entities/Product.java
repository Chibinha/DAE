package com.example.backend.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    private String name;
    private String description;
    private double weight;
    private String ingredients;
    @OneToMany(mappedBy = "product")
    private List<PhysicalProduct> physicalProducts;

    public Product() {
        this.physicalProducts = new ArrayList<>();
    }

    public Product(Long id, String name, String description, double weight) {
        this.product_id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.ingredients = "";
        this.physicalProducts = new ArrayList<>();
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<PhysicalProduct> getPhysicalProducts() {
        return physicalProducts;
    }

    public void setPhysicalProducts(List<PhysicalProduct> physicalProducts) {
        this.physicalProducts = physicalProducts;
    }
}
