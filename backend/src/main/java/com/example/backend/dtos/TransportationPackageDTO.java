package com.example.backend.dtos;

import java.util.LinkedList;
import java.util.List;

public class TransportationPackageDTO {
    protected long id;
    protected int packageType;
    protected String material;
    protected long order_id;

    public TransportationPackageDTO() {}

    public TransportationPackageDTO(long id, int packageType, String material, long order_id) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.order_id = order_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int packageType) {
        this.packageType = packageType;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getOrderId() {
        return order_id;
    }

    public void setOrderId(long order_id) {
        this.order_id = order_id;
    }
}
