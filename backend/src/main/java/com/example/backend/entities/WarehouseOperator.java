package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllLineOperators",
                query = "SELECT l FROM WarehouseOperator l ORDER BY l.name" // JPQL
        )
})
public class WarehouseOperator extends User {
   @OneToMany(mappedBy = "warehouseOperator", cascade = CascadeType.REMOVE)
   private List<Order> orders;

    public WarehouseOperator(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new ArrayList<Order>();
    }

    public WarehouseOperator() {
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
