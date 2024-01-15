package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "orders",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idOrder"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.idOrder"
        )
})
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOrder;

    @NotNull
    @Column(name = "orderType")
    private String orderType;

    @Column(name = "materialType")
    private String materialType;

    @OneToMany(mappedBy = "product")
    private List<PhysicalProduct> products;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @NotNull
    public Client client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderTimestamp")
    @NotNull
    private Timestamp orderTimestamp;

    public Order() {
        this.products = new ArrayList<>();
        //this.orderTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public Order(long idOrder, String orderType, String materialType, Client client) {
        this.idOrder = idOrder;
        this.orderType = orderType;
        this.materialType = materialType;
        this.products = new ArrayList<>();
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.client = new Client();
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public List<PhysicalProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PhysicalProduct> products) {
        this.products = products;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }
}
