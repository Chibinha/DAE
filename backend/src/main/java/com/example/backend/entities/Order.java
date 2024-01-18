package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "orders")
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
    private String status;
    @Column(name = "total_price")
    private double totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<PhysicalProduct> physicalProducts;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Observation> observations;

    @ManyToOne
    @JoinColumn(name = "client")
    @NotNull
    public Client client;

    @ManyToOne
    @JoinColumn(name = "lineOperator")
    @NotNull
    public LineOperator lineOperator;

    @ManyToMany(mappedBy = "orders")
    private List<Sensor> sensors;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderTimestamp")
    @NotNull
    private Timestamp orderTimestamp;

    public Order() {
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.physicalProducts = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public Order(double totalPrice, LineOperator lineOperator, Client client, List<PhysicalProduct> physicalProducts) {
        this.status = "created";
        this.totalPrice = totalPrice;
        this.client = client;
        this.lineOperator = lineOperator;
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.physicalProducts = physicalProducts;
        this.observations = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long idOrder) {
        this.id = idOrder;
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

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
