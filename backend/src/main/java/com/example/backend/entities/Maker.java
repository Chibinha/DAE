package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllMakers",
                query = "SELECT m FROM Maker m ORDER BY m.name" // JPQL
        )
})
public class Maker extends User {
    @OneToMany(mappedBy = "maker", cascade = CascadeType.REMOVE)
    private List<Product> products;
    @OneToMany(mappedBy = "maker", cascade = CascadeType.REMOVE)
    private List<PhysicalProduct> physicalProducts;

    public Maker() {
        this.products = new ArrayList<>();
        this.physicalProducts = new ArrayList<>();
    }

    public Maker(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.products = new ArrayList<Product>();
        this.physicalProducts = new ArrayList<PhysicalProduct>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<PhysicalProduct> getPhysicalProducts() {
        return physicalProducts;
    }

    public void setPhysicalProducts(List<PhysicalProduct> physicalProducts) {
        this.physicalProducts = physicalProducts;
    }
}
