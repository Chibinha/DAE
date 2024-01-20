package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// in stock since

@Entity
@Table(name = "inventory_items")
@NamedQueries({
    @NamedQuery(
        name = "getAllInventoryItems",
        query = "SELECT p FROM InventoryItem p ORDER BY p.id"
    ),
    @NamedQuery(
        name = "getMakerInventoryItems",
        query = "SELECT p FROM InventoryItem p WHERE p.product.manufacturer.username = :username ORDER BY p.id"
    ),
    @NamedQuery(
        name = "getMakerInventoryItemsForProduct",
        query = "SELECT p FROM InventoryItem p WHERE p.product.manufacturer.username = :username AND p.product.id = :productId ORDER BY p.id"
    ),// fetch List<ProductPackage> productPackages;


})
public class InventoryItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToMany
    @JoinTable(
        name = "productpackages_inventoryitems",
        joinColumns = @JoinColumn(name = "inventory_item_id"),
        inverseJoinColumns = @JoinColumn(name = "product_package_id")
    )
    private List<ProductPackage> productPackages;

    // manufacturer
    @ManyToOne
    @JoinColumn(name = "maker_id")
    @NotNull
    private Manufacturer manufacturer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "stock_timestamp")
    @NotNull
    private Timestamp stockTimestamp;

    public InventoryItem() {
        this.productPackages = new ArrayList<>();
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public InventoryItem(Product product) {
        this.product = product;
        this.manufacturer = product.getMaker();
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
        this.order = null;
        this.productPackages = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        order.addInventoryItem(this);
    }

    public List<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackage> productPackages) {
        // manage the other side of the relationship
        this.productPackages = productPackages;
    }

    // Add ProductPackage
    public void addProductPackage(ProductPackage productPackage) {
        if (!productPackages.contains(productPackage)) {
            productPackages.add(productPackage);
            productPackage.getInventoryItems().add(this); // Manage the other side of the relationship
        }
    }

    // Remove ProductPackage
    public void removeProductPackage(ProductPackage productPackage) {
        if (productPackages.contains(productPackage)) {
            productPackages.remove(productPackage);
            productPackage.removeInventoryItem(this);
        }
    }

    public Manufacturer getMaker() {
        return manufacturer;
    }

    public void setMaker(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Timestamp getStockTimestamp() {
        return stockTimestamp;
    }

    public void setStockTimestamp(Timestamp inStockSince) {
        this.stockTimestamp = inStockSince;
    }
}
