package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTransportationPackages",
                query = "SELECT t FROM TransportationPackage t ORDER BY t.id" // JPQL
        )
})
public class TransportationPackage extends Package implements Serializable {
    @ManyToMany
    @JoinTable(
            name = "packages_orders",
            joinColumns = @JoinColumn(name = "transportation_package_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "order_id"),

            }
    )
    private List<Order> orders;
    public TransportationPackage() {
        this.orders = new ArrayList<>();
    }

    public TransportationPackage(int packageType, String material) {
        super(packageType, material);
        this.orders = new ArrayList<>();
    }

    public Order getCurrentOrder() {
        if(!orders.isEmpty())
            return orders.get(orders.size() - 1);
        return null;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void setOrders(List<Order> orders) {
        this.orders = orders;
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
