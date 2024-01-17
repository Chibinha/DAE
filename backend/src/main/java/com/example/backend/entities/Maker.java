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

    public Maker() {
        this.products = new ArrayList<>();
    }

    public Maker(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.products = new ArrayList<Product>();
    }
}
