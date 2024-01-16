package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Observation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String type;
    private String value;
    private String unit;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @NotNull
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "id_package")
    private Package myPackage;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Observation() {
    }

    public Observation(int id, String type, String value, String unit, Timestamp timestamp, Sensor sensor, Package myPackage) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.sensor = sensor;
        this.myPackage = myPackage;
        this.order = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Package getMyPackage() {
        return myPackage;
    }

    public void setMyPackage(Package myPackage) {
        this.myPackage = myPackage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
