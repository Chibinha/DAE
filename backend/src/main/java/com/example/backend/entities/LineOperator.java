package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllLineOperators",
                query = "SELECT l FROM LineOperator l ORDER BY l.name" // JPQL
        )
})
public class LineOperator extends User{
   @OneToMany(mappedBy = "lineOperator", cascade = CascadeType.REMOVE)
   private List<Order> orders;

    public LineOperator(String username, String password, String name, String email) {
        super(username, password, name, email);
        this.orders = new ArrayList<Order>();
    }

    public LineOperator() {
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
