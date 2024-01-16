package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Client c ORDER BY c.name" // JPQL
        )
})
public class Client extends User {
    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private List<Order> orders;

//    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
//    private List<Alert> alerts;

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new ArrayList<Order>();
//        this.alerts = new ArrayList<Alert>();
    }

    public Client() {
        this.orders = new ArrayList<Order>();
//        this.alerts = new ArrayList<Alert>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Order order) {
        if(order!= null)
            this.orders.add(order);
    }

    public void removeOrder(Order order) {
        if(order!= null)
            this.orders.remove(order);
    }

//    public List<Alert> getAlerts() {
//        return alerts;
//    }
}
