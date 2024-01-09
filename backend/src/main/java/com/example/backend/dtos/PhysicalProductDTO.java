package com.example.backend.dtos;

import java.sql.Timestamp;

public class PhysicalProductDTO {
    private Long id;
    private long productId;
    private String productName;
    private String serialNumber;
    private Timestamp stockTimestamp;

    public PhysicalProductDTO() {
    }

    public PhysicalProductDTO(Long id, long productId, String productName, String serialNumber, Timestamp stockTimestamp) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.serialNumber = serialNumber;
        this.stockTimestamp = stockTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Timestamp getStockTimestamp() {
        return stockTimestamp;
    }

    public void setStockTimestamp(Timestamp stockTimestamp) {
        this.stockTimestamp = stockTimestamp;
    }
}
