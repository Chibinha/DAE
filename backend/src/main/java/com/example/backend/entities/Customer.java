package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT c FROM Customer c ORDER BY c.name" // JPQL
        )
})
public class Customer extends User {
    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
    private List<Order> orders;

//    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
//    private List<Alert> alerts;

    public Customer(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new ArrayList<Order>();
//        this.alerts = new ArrayList<Alert>();
    }

    public Customer() {
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
