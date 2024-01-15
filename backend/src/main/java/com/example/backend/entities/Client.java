package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Encoded;
import com.example.backend.entities.Order;

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

    public Client(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new ArrayList<Order>();
    }

    public Client() {
        this.orders = new ArrayList<Order>();
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
}
