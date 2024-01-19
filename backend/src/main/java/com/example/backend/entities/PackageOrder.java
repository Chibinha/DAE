package com.example.backend.entities;

import com.example.backend.entities.Order;
import com.example.backend.entities.Package;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "package_order")
public class PackageOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package orderPackage;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "associationDate")
    @NotNull
    private Timestamp associationDate;


    public PackageOrder(Package orderPackage, Order order, Timestamp associationDate) {
        this.orderPackage = orderPackage;
        this.order = order;
        this.associationDate = associationDate;
    }

    public PackageOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Package getOrderPackage() {
        return orderPackage;
    }

    public void setOrderPackage(Package orderPackage) {
        this.orderPackage = orderPackage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Timestamp getAssociationDate() {
        return associationDate;
    }

    public void setAssociationDate(Timestamp associationDate) {
        this.associationDate = associationDate;
    }
}
