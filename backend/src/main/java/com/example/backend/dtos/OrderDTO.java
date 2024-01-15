package com.example.backend.dtos;

import com.example.backend.entities.Client;
import com.example.backend.entities.PhysicalProduct;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idOrder;
    @Temporal(TemporalType.DATE)
    private Date orderDate;
    @NotNull
    private String orderType;
    private String materialType;
    @OneToMany(mappedBy = "product")
    private List<PhysicalProduct> products;
    /*//@OneToMany(mappedBy = "observation")
    //private List<Observation> observation;
    @OneToMany(mappedBy = "client")
    private List<Client> client;*/
    public OrderDTO() {
        this.products = new ArrayList<>();
    }

    public OrderDTO(long idOrder, Date orderDate, String orderType, String materialType) {
        this.idOrder = idOrder;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.materialType = materialType;
        this.products = new ArrayList<>();
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public List<PhysicalProduct> getProducts() {
        return products;
    }

    public void setProducts(List<PhysicalProduct> products) {
        this.products = products;
    }
}
