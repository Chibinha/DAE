package com.example.backend.entities;

import jakarta.persistence.Entity;

@Entity
public class Maker extends User {
    public Maker(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    public Maker() {
    }
}
