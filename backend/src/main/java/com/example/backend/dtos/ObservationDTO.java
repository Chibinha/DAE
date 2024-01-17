package com.example.backend.dtos;

import java.io.Serializable;
import java.sql.Timestamp;

public class ObservationDTO implements Serializable {
    private long id;
    private String type;
    private String value;
    private String unit;
    private Timestamp timestamp;
    private long sensorId;
    private long packageId;
    private long orderId;

    public ObservationDTO() {
    }

    public ObservationDTO(long id, String type, String value, String unit, Timestamp timestamp, long sensorId, long packageId, long orderId) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.packageId = packageId;
        this.orderId = orderId;
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

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getPackageId() {
        return packageId;
    }

    public void setPackageId(long packageId) {
        this.packageId = packageId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
