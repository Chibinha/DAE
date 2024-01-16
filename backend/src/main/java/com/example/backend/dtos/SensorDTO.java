package com.example.backend.dtos;

public class SensorDTO {
    protected String name;
    protected String sensorType;
    protected String unit;

    public SensorDTO() {

    }

    public SensorDTO(String name, String sensorType, String unit) {
        this.name = name;
        this.sensorType = sensorType;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
