package com.example.backend.dtos;

public class ProductPackageDTO {
    private long id;
    private String type;
    private String material;

    public ProductPackageDTO() {}

    public ProductPackageDTO(long id,String type, String material) {
        this.id = id;
        this.type = type;
        this.material = material;
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

}
