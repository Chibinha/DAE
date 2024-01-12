package com.example.backend.dtos;

public class ProductDTO {
    private Long product_id;
    private String name;
    private String description;
    private double weight;
    private String ingredients;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, double weight, String ingredients) {
        this.product_id = id;
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.ingredients = ingredients;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long id) {
        this.product_id = id;
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
