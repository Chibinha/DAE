package com.example.backend.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

public class OrderDTO implements Serializable {
    private long id;
    private String status;
    private double totalPrice;
    private String lineOperator;
    private List<InventoryItemDTO> inventoryItems;
    public String client;
    private Timestamp orderTimestamp;
    public OrderDTO() {
        this.inventoryItems = new ArrayList<>();
    }

    public OrderDTO(long id, String status, double totalPrice, String lineOperator, String client, Timestamp orderTimestamp, List<InventoryItemDTO> inventoryItems) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.lineOperator = lineOperator;
        this.client = client;
        this.inventoryItems = inventoryItems;
        this.orderTimestamp = orderTimestamp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLineOperator() {
        return lineOperator;
    }

    public void setLineOperator(String lineOperator) {
        this.lineOperator = lineOperator;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<InventoryItemDTO> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItemDTO> inventoryItems) {
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

    public Timestamp getOrderTimestamp() {
        return orderTimestamp;
    }
}
