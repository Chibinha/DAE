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
                name = "getAllPhysicalProducts",
                query = "SELECT p FROM InventoryItem p ORDER BY p.id"
        ),
        @NamedQuery(
                name = "getMakerPhysicalProducts",
                query = "SELECT p FROM InventoryItem p WHERE p.product.manufacturer.username = :username ORDER BY p.id"
        ),//getMakerPhysicalProductsForProduct
        @NamedQuery(
                name = "getMakerPhysicalProductsForProduct",
                query = "SELECT p FROM InventoryItem p WHERE p.product.manufacturer.username = :username AND p.product.id = :productId ORDER BY p.id"
        ),
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
    @JoinColumn(name = "package_id")
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
        this.productPackages = new ArrayList<>();
        this.manufacturer = product.getMaker();
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
        this.product.addPhysicalProduct(this);
        this.order = null;
    }

    public InventoryItem(Product product, List<ProductPackage> productPackages) {
        this.product = product;
        this.productPackages = productPackages;
        this.manufacturer = product.getMaker();
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
        this.product.addPhysicalProduct(this);
        this.order = null;
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
    }

    public List<ProductPackage> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackage> productPackages) {
        this.productPackages = productPackages;
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
