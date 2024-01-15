package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;

// in stock since

@Entity
@Table(
        name = "physical_product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "serial_number"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllPhysicalProducts",
                query = "SELECT p FROM PhysicalProduct p ORDER BY p.id"
        )
})
public class PhysicalProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "serial_number")
    @NotNull
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "stock_timestamp")
    @NotNull
    private Timestamp stockTimestamp;

    public PhysicalProduct() {
    }

    public PhysicalProduct(Product product, String serialNumber) {
        this.product = product;
        this.serialNumber = serialNumber;
        this.stockTimestamp = new Timestamp(System.currentTimeMillis());
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Timestamp getStockTimestamp() {
        return stockTimestamp;
    }

    public void setStockTimestamp(Timestamp inStockSince) {
        this.stockTimestamp = inStockSince;
    }
}
