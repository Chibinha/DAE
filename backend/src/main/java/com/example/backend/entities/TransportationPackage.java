package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllTransportationPackages",
                query = "SELECT t FROM TransportationPackage t ORDER BY t.id" // JPQL
        )
})
public class TransportationPackage extends Package implements Serializable {
    @ManyToOne
    @JoinColumn(name = "order_id")
    @NotNull
    private Order order;
    public TransportationPackage() {
    }

    public TransportationPackage(long id, int packageType, String material, Order order) {
        super(id, packageType, material);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
