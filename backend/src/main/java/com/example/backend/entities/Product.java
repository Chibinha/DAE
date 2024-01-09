package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    private String description;
    private double weight;
    private String ingredients;
    @OneToMany(mappedBy = "product")
    private List<PhysicalProduct> physicalProducts;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_timestamp")
    @NotNull
    private Timestamp creationTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_timestamp")
    @NotNull
    private Timestamp updateTimestamp;

    public Product() {
        this.physicalProducts = new ArrayList<>();
    }

    public Product(String name, String description, double weight, String ingredients) {
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.ingredients = "";
        this.physicalProducts = new ArrayList<>();
        this.creationTimestamp = new Timestamp(System.currentTimeMillis());
        this.updateTimestamp = this.creationTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long product_id) {
        this.id = product_id;
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
