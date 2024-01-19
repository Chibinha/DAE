package com.example.backend.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

public class PhysicalProductDTO implements Serializable {
    private long id;
    private long productId;
    private String productName;
    private String makerUsername;
    private Timestamp stockTimestamp;

    public PhysicalProductDTO() {
    }

    public PhysicalProductDTO(long id, long productId, String productName, String makerUsername, Timestamp stockTimestamp) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.makerUsername = makerUsername;
        this.stockTimestamp = stockTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getMakerUsername() {
        return makerUsername;
    }

    public void setMakerUsername(String makerUsername) {
        this.makerUsername = makerUsername;
    }

    public Timestamp getStockTimestamp() {
        return stockTimestamp;
    }

    public void setStockTimestamp(Timestamp stockTimestamp) {
        this.stockTimestamp = stockTimestamp;
    }
}
