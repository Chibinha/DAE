package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

// in stock since

@Entity
@Table(name = "physical_product")
@NamedQueries({
        @NamedQuery(
                name = "getAllPhysicalProducts",
                query = "SELECT p FROM PhysicalProduct p ORDER BY p.id"
        ),
        @NamedQuery(
                name = "getMakerPhysicalProducts",
                query = "SELECT p FROM PhysicalProduct p WHERE p.product.maker.username = :username ORDER BY p.id"
        ),//getMakerPhysicalProductsForProduct
        @NamedQuery(
                name = "getMakerPhysicalProductsForProduct",
                query = "SELECT p FROM PhysicalProduct p WHERE p.product.maker.username = :username AND p.product.id = :productId ORDER BY p.id"
        ),
})
public class PhysicalProduct implements Serializable {
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

    @ManyToMany(mappedBy = "physicalProducts")
    private List<ProductPackage> productPackages;

    // maker
    @ManyToOne
    @JoinColumn(name = "maker_id")
    @NotNull
    private Maker maker;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "stock_timestamp")
    @NotNull
    private Timestamp stockTimestamp;

    public PhysicalProduct() {
        this.productPackages = new ArrayList<>();
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public PhysicalProduct(Product product) {
        this.product = product;
        this.productPackages = new ArrayList<>();
        this.maker = product.getMaker();
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
        this.product.addPhysicalProduct(this);
        this.order = null;
    }

    public PhysicalProduct(Product product, List<ProductPackage> productPackages) {
        this.product = product;
        this.productPackages = productPackages;
        this.maker = product.getMaker();
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

    public Maker getMaker() {
        return maker;
    }

    public void setMaker(Maker maker) {
        this.maker = maker;
    }

    public Timestamp getStockTimestamp() {
        return stockTimestamp;
    }

    public void setStockTimestamp(Timestamp inStockSince) {
        this.stockTimestamp = inStockSince;
    }
}
