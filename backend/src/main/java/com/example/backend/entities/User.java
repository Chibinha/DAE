package com.example.backend.entities;

import com.example.backend.entities.Versionable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class User extends Versionable {
    @Id
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String name;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Alert> alerts;

    @Email
    @NotNull
    private String email;

    public User() {
        this.alerts = new ArrayList<Alert>();
    }

    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.alerts = new ArrayList<Alert>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }
}
