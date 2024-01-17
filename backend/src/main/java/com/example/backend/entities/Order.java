package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(
        name = "orders",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idOrder"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        )
})
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "orderType")
    private String type;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<PhysicalProduct> physicalProducts;

    @ManyToOne
    @JoinColumn(name = "client")
    @NotNull
    public Client client;

    @ManyToOne
    @JoinColumn(name = "lineOperator")
    @NotNull
    public LineOperator lineOperator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderTimestamp")
    @NotNull
    private Timestamp orderTimestamp;

    public Order() {
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.physicalProducts = new ArrayList<>();
    }

    public Order(String type, LineOperator lineOperator, Client client, List<PhysicalProduct> physicalProducts) {
        this.type = type;
        this.client = client;
        this.lineOperator = lineOperator;
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.physicalProducts = physicalProducts;
    }

    public long getId() {
        return id;
    }

    public void setId(long idOrder) {
        this.id = idOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String orderType) {
        this.type = orderType;
    }

    public LineOperator getLineOperator() {
        return lineOperator;
    }

    public void setLineOperator(LineOperator lineOperator) {
        this.lineOperator = lineOperator;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public List<PhysicalProduct> getPhysicalProducts() {
        return physicalProducts;
    }

    public void setPhysicalProducts(List<PhysicalProduct> physicalProducts) {
        this.physicalProducts = physicalProducts;
    }
}
