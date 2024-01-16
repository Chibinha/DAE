package com.example.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Sensor implements Serializable {
    @Id
    private String name;
    private String sensorType;
    private String unit;

    public Sensor() {

    }

    public Sensor(String name, String sensorType, String unit) {
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
