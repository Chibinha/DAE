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

    @ElementCollection
    @CollectionTable(name = "order_product_quantities", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "product_id")
    @Column(name = "productQuantities")
    private Map<Long, Integer> productQuantities = new HashMap<>();

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
        this.productQuantities = new HashMap<>();
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public Order(String orderType, LineOperator lineOperator,Client client) {
        this.orderType = orderType;
        this.client = client;
        this.lineOperator = lineOperator;
        this.productQuantities = new HashMap<>();
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
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

    public void addProductQuantity(PhysicalProduct physicalProduct, int quantity) {
        if (physicalProduct != null) {

            Long productId = physicalProduct.getId();

            if (productQuantities.containsKey(productId)) {
                int existingQuantity = productQuantities.get(productId);
                productQuantities.put(productId, existingQuantity + quantity);
            } else {
                productQuantities.put(productId, quantity);
            }
        }
    }

    public void removeProductQuantity(Long productId) {
        this.productQuantities.remove(productId);
    }

    public Map<Long, Integer> getProducts() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}
