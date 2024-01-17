package com.example.backend.dtos;

import com.example.backend.entities.Client;
import com.example.backend.entities.LineOperator;
import com.example.backend.entities.PhysicalProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.sound.sampled.Line;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class OrderDTO implements Serializable {
    private long idOrder;
    private String orderType;
    private LineOperator lineOperator;
    private List<PhysicalProductDTO> products;
    public Client client;
    public OrderDTO() {
        this.products = new ArrayList<>();
    }

    public OrderDTO(String orderType, LineOperator lineOperator, Client client, List<PhysicalProductDTO> products) {
        this.orderType = orderType;
        this.lineOperator = lineOperator;
        this.client = client;
        this.products = products;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public LineOperator getLineOperator() {
        return lineOperator;
    }

    public void setLineOperator(LineOperator lineOperator) {
        this.lineOperator = lineOperator;
    }

    public List<PhysicalProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<PhysicalProductDTO> products) {
        this.products = products;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
