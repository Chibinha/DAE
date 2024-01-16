package com.example.backend.dtos;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private long id;
    private String name;
    private double price;
    private String description;
    private double weight;
    private String ingredients;
    private long inStock;


    private String makerName;

    public ProductDTO() {
    }

    public ProductDTO(long id, String name, double price, String description, double weight, String ingredients, long inStock, String makerName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.weight = weight;
        this.ingredients = ingredients;
        this.inStock = inStock;
        this.makerName = makerName;
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

    public long getInStock() {
        return inStock;
    }

    public void setInStock(long inStock) {
        this.inStock = inStock;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }
}
