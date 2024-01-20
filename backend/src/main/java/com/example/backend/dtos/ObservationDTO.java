package com.example.backend.dtos;

import com.example.backend.entities.Sensor;

import java.io.Serializable;
import java.sql.Timestamp;

public class ObservationDTO implements Serializable {
    private long id;
    private String value;
    private long sensorId;
    private long orderId;

    public ObservationDTO() {
    }

    public ObservationDTO(long id, String value, long sensorId, long orderId) {
        this.id = id;
        this.value = value;
        this.sensorId = sensorId;
        this.orderId = orderId;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
