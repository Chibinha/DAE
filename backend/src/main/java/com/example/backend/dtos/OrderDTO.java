package com.example.backend.dtos;

import java.io.Serializable;
import java.util.*;

public class OrderDTO implements Serializable {
    private long id;
    private String type;
    private String lineOperator;
    private List<PhysicalProductDTO> physicalProducts;
    public String client;
    public OrderDTO() {
        this.physicalProducts = new ArrayList<>();
    }

    public OrderDTO(String type, String lineOperator, String client, List<PhysicalProductDTO> physicalProducts) {
        this.type = type;
        this.lineOperator = lineOperator;
        this.client = client;
        this.physicalProducts = physicalProducts;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public List<PhysicalProductDTO> getPhysicalProducts() {
        return physicalProducts;
    }

    public void setPhysicalProducts(List<PhysicalProductDTO> physicalProducts) {
        this.physicalProducts = physicalProducts;
    }
}
