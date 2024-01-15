package com.example.backend.dtos;

import com.example.backend.entities.Sensor;

import java.util.LinkedList;
import java.util.List;

public class ProductPackageDTO {
    private long id;
    private int packageType;
    private String material;
    private List<SensorDTO> values;
    private long product_id;

    public ProductPackageDTO() {
        this.values = new LinkedList<SensorDTO>();
    }

    public ProductPackageDTO(long id, int packageType, String material, long product_id) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.product_id = product_id;
        this.values = new LinkedList<SensorDTO>();
    }

    public ProductPackageDTO(long id, int packageType, String material, long product_id, List<SensorDTO> values) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.product_id = product_id;
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

    public long getProductId() {
        return product_id;
    }

    public void setProductId(long product_id) {
        this.product_id = product_id;
    }
}
