package com.example.backend.dtos;

import java.util.LinkedList;
import java.util.List;

public class TransportationPackageDTO {
    protected long id;
    protected int packageType;
    protected String material;
    protected List<SensorDTO> values;
    protected long order_id;

    public TransportationPackageDTO() {
        this.values = new LinkedList<SensorDTO>();
    }

    public TransportationPackageDTO(long id, int packageType, String material, int order_id) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.values = new LinkedList<SensorDTO>();
        this.order_id = order_id;
    }

    public TransportationPackageDTO(long id, int packageType, String material, long order_id, List<SensorDTO> values) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.order_id = order_id;
        this.values = new LinkedList<SensorDTO>();
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

    public List<SensorDTO> getValues() {
        return values;
    }

    public void setValues(List<SensorDTO> values) {
        this.values = values;
    }


    public long getOrderId() {
        return order_id;
    }

    public void setOrderId(long order_id) {
        this.order_id = order_id;
    }
}
