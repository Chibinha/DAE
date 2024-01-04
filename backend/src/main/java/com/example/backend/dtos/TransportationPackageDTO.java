package com.example.backend.dtos;

import java.util.LinkedList;
import java.util.List;

public class TransportationPackageDTO {
    protected long id;
    protected int packageType;
    protected String material;
    protected List<SensorDTO> valores;
    protected OrderDTO order;

    public TransportationPackageDTO() {
        this.valores = new LinkedList<SensorDTO>();
    }

    public TransportationPackageDTO(long id, int packageType, String material, OrderDTO order) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.valores = new LinkedList<SensorDTO>();
        this.order = order;
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

    public List<SensorDTO> getValores() {
        return valores;
    }

    public void setValores(List<SensorDTO> valores) {
        this.valores = valores;
    }


    public OrderDTO getOrder() {
        return order;
    }

    public void setOrder(OrderDTO order) {
        this.order = order;
    }
}
