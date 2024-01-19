package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "observations")
@NamedQueries({
        @NamedQuery(
                name = "getAllObservations",
                query = "SELECT o FROM Observation o ORDER BY o.id"
        )
})
public class Observation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String value;
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Timestamp timestamp;
    @ManyToOne
    @JoinColumn(name = "sensor_id")
    @NotNull
    private Sensor sensor;

    public Observation() {
    }

    public Observation(String value, Sensor sensor) {
        this.value = value;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.sensor = sensor;
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

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
