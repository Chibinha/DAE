package com.example.backend.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private long id;
    private String name;
    private String description;
    private double weight;
    private String ingredients;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, String description, double weight, String ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.ingredients = ingredients;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
