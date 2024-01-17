package com.example.backend.dtos;

import com.example.backend.entities.Client;
import com.example.backend.entities.LineOperator;
import com.example.backend.entities.PhysicalProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.sound.sampled.Line;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class OrderDTO implements Serializable {
    private long idOrder;
    private String orderType;
    private String lineOperator;
    private Map<Long, Integer> productQuantities;
    public String client;
    public OrderDTO() {
        this.productQuantities = new HashMap<>();
    }

    public OrderDTO(String orderType, String lineOperator, String client, Map<Long, Integer> productQuantities) {
        this.orderType = orderType;
        this.lineOperator = lineOperator;
        this.client = client;
        this.productQuantities = productQuantities;
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

    public String getLineOperator() {
        return lineOperator;
    }

    public void setLineOperator(String lineOperator) {
        this.lineOperator = lineOperator;
    }

    public Map<Long, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Long, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }


    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
