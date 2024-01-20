package com.example.backend.dtos;

import com.example.backend.entities.ProductPackage;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InventoryItemDTO implements Serializable {
    private long id;
    private long productId;
    private String productName;
    private String makerUsername;
    private Timestamp stockTimestamp;
    private List<ProductPackageDTO> productPackages;

    public InventoryItemDTO() {
        this.productPackages = new ArrayList<>();
    }

    public InventoryItemDTO(long id, long productId, String productName, String makerUsername, Timestamp stockTimestamp, List<ProductPackageDTO> productPackages) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.makerUsername = makerUsername;
        this.stockTimestamp = stockTimestamp;
        this.productPackages = productPackages;
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

    public List<ProductPackageDTO> getProductPackages() {
        return productPackages;
    }

    public void setProductPackages(List<ProductPackageDTO> productPackages) {
        this.productPackages = productPackages;
    }

    public void addProductPackage(ProductPackageDTO productPackage) {
        if (!productPackages.contains(productPackage)) {
            productPackages.add(productPackage);
        }
    }

    public void removeProductPackage(ProductPackageDTO productPackage) {
        productPackages.remove(productPackage);
    }
}
