package com.example.backend.dtos;

public class TransportPackageDTO {
    protected long id;
    protected String type;
    protected String material;
    protected long order_id;

    public TransportPackageDTO() {}

    public TransportPackageDTO(String type, String material, long order_id) {
        this.type = type;
        this.material = material;
        this.order_id = order_id;
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
