package com.example.backend.dtos;

import com.example.backend.entities.Client;
import com.example.backend.entities.LineOperator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDTO implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private long price;
    private String status;
    private List<PhysicalProductDTO> products;
    public Client client;
    public LineOperator lineOperator;
    private Timestamp orderTimestamp;
    public OrderDTO() {
        this.products = new ArrayList<>();
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public OrderDTO(String type, String status, LineOperator lineOperator, Client client, List<PhysicalProductDTO> products) {
        this.type = type;
        this.status = status;
        this.lineOperator = lineOperator;
        this.client = client;
        this.products = products;
        this.orderTimestamp = new Timestamp(System.currentTimeMillis());
    }

    public long getId() {
        return id;
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

    public List<PhysicalProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<PhysicalProductDTO> products) {
        this.products = products;
    }
}
