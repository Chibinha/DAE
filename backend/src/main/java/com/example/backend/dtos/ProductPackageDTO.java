package com.example.backend.dtos;

import com.example.backend.entities.Sensor;

import java.util.LinkedList;
import java.util.List;

public class ProductPackageDTO {
    private long id;
    private int packageType;
    private String material;
    private List<SensorDTO> valores;
    private long product_id;

    public ProductPackageDTO() {
        this.valores = new LinkedList<SensorDTO>();
    }

    public ProductPackageDTO(long id, int packageType, String material, long product_id) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.product_id = product_id;
        this.valores = new LinkedList<SensorDTO>();
    }

    public ProductPackageDTO(long id, int packageType, String material, long product_id, List<SensorDTO> valores) {
        this.id = id;
        this.packageType = packageType;
        this.material = material;
        this.product_id = product_id;
        this.valores = new LinkedList<SensorDTO>();
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

    public long getProductId() {
        return product_id;
    }

    public void setProductId(long product_id) {
        this.product_id = product_id;
    }
}
