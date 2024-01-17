package com.example.backend.dtos;

import java.io.Serializable;

public class MakerDTO implements Serializable {
    private String username;
    private String name;
    private String email;

    public MakerDTO() {
    }

    public MakerDTO(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
