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
    @Column(name = "type")
    private String type;

    @NotNull
    @Column(name = "price")
    private double price;

    @NotNull
    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "order")
    @Column(name= "products")
    private List<PhysicalProduct> products;

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
        this.products = new ArrayList<>();
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public Order(String type, String status, LineOperator lineOperator,Client client, List<PhysicalProduct> physicalProducts) {
        this.type = type;
        this.status = status;
        this.lineOperator = lineOperator;
        this.client = client;
        this.products = physicalProducts;
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public long getIdOrder() {
        return id;
    }

    public void setId(long idOrder) {
        this.id = idOrder;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
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

    public List<PhysicalProduct> getProducts() {
        return new ArrayList<>(products);
    }

    public void setProducts(List<PhysicalProduct> products) {
        this.products = products;
    }

    public void addProducts(PhysicalProduct product) {
        this.products.add(product);
    }

    public void removeProducts(PhysicalProduct product) {
        this.products.remove(product);
    }

    /*public void addProductQuantity(PhysicalProduct physicalProduct, int quantity) {
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

    public int calculateTotalPrice() {
        return productQuantities.values().stream().mapToInt(Integer::intValue).sum();
    }
    */
}
