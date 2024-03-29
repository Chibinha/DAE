package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@NamedQueries({
        @NamedQuery(
                name = "getAllOrders",
                query = "SELECT o FROM Order o ORDER BY o.id"
        ),//getOrderSensors - get all sensors from an order
    @NamedQuery(
        name = "getOrderSensors",
        query = "SELECT DISTINCT sensor FROM Order o JOIN o.packages p JOIN p.sensors sensor WHERE sensor.id = :sensorId ORDER BY o.id"
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

    @ManyToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    private List<TransportPackage> packages;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "orderTimestamp")
    @NotNull
    private Timestamp orderTimestamp;

    public Order() {
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.inventoryItems = new ArrayList<>();
        this.packages = new ArrayList<>();
        this.observations = new ArrayList<>();
    }

    public Order(double totalPrice, WarehouseOperator warehouseOperator, Customer customer, List<InventoryItem> inventoryItems) {
        this.status = "Criada";
        this.totalPrice = totalPrice;
        this.customer = customer;
        this.warehouseOperator = warehouseOperator;
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
        this.inventoryItems = inventoryItems;
        this.packages = new ArrayList<>();
        this.observations = new ArrayList<>();
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

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public WarehouseOperator getWarehouseOperator() {
        return warehouseOperator;
    }

    public void setWarehouseOperator(WarehouseOperator warehouseOperator) {
        this.warehouseOperator = warehouseOperator;
    }

    public List<TransportPackage> getPackages() {
        return packages;
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public TransportPackage getCurrentPackage() {
        if(!packages.isEmpty() && packages.get(packages.size() - 1).getCurrentOrder().getId() == this.getId()) {
            return packages.get(packages.size() - 1);
        }
        TransportPackage empty = new TransportPackage();
        empty.setId(-1);
        return empty;
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

    public void addInventoryItem(InventoryItem inventoryItem) {
        if(inventoryItem != null)
            this.inventoryItems.add(inventoryItem);
    }
}
