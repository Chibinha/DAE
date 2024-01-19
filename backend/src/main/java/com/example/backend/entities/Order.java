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
    private List<InventoryItem> inventoryItems;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE)
    private List<Observation> observations;

    @ManyToOne
    @JoinColumn(name = "customer")
    @NotNull
    public Customer customer;

    @ManyToOne
    @JoinColumn(name = "warehouseOperator")
    @NotNull
    public WarehouseOperator warehouseOperator;

    @ManyToMany(mappedBy = "orders")
    private List<TransportPackage> packages;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderTimestamp")
    @NotNull
    private Timestamp orderTimestamp;

    public Order() {
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.inventoryItems = new ArrayList<>();
        this.observations = new ArrayList<>();
        this.packages = new ArrayList<>();
    }

    public Order(double totalPrice, WarehouseOperator warehouseOperator, Customer customer, List<InventoryItem> inventoryItems) {
        this.status = "created";
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.warehouseOperator = warehouseOperator;
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.inventoryItems = inventoryItems;
        this.observations = new ArrayList<>();
        this.packages = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long idOrder) {
        this.id = idOrder;
    }

    public WarehouseOperator getLineOperator() {
        return warehouseOperator;
    }

    public void setLineOperator(WarehouseOperator warehouseOperator) {
        this.warehouseOperator = warehouseOperator;
    }

    public Customer getClient() {
        return customer;
    }

    public void setClient(Customer customer) {
        this.customer = customer;
    }

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }

    public void setOrderTimestamp(Timestamp orderTimestamp) {
        this.orderTimestamp = orderTimestamp;
    }

    public List<InventoryItem> getPhysicalProducts() {
        return inventoryItems;
    }

    public void setPhysicalProducts(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
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

    public List<TransportPackage> getPackages() {
        return packages;
    }

    public void setPackages(List<TransportPackage> packages) {
        this.packages = packages;
    }

    public void addPackage(TransportPackage transportPackage) {
        if(transportPackage != null)
            this.packages.add(transportPackage);
    }

    public void removePackage(TransportPackage order) {
        if(order!= null)
            this.packages.remove(order);
    }
}
