package com.example.backend.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sensor")
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT s FROM Sensor s ORDER BY s.id"
        )
})
public class Sensor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    private String unit;

    @OneToMany(mappedBy = "sensor")
    private List<Observation> observations;

    @ManyToMany
    @JoinTable(
            name = "sensors_orders",
            joinColumns = @JoinColumn(name = "sensor_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "order_id"),

            }
    )
    private List<Order> orders;

    public Sensor() {
        this.observations = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public Sensor(String name, String type, String unit) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.observations = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
