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

//    @ManyToOne
//    @JoinColumn(name = "id_package")
//    private Package package;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Observation() {
    }

    public Observation(int id, String type, String value, String unit, Timestamp timestamp, Sensor sensor, Order order) {
        this.id = id;
        this.type = type;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
        this.sensor = sensor;
        this.order = order;
    }
}
