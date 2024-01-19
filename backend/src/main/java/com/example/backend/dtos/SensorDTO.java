package com.example.backend.dtos;

public class SensorDTO {
    private long id;
    protected String name;
    protected String type;
    private String unit;
    private long lastPackage;

    public SensorDTO() {

    }

    public SensorDTO(long id, String name, String type, String unit, long lastPackage) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.lastPackage = lastPackage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public long getLastPackage() {
        return lastPackage;
    }

    public void setLastPackage(long lastPackage) {
        this.lastPackage = lastPackage;
    }
}
