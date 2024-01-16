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
    private int id;
    private String name;
    private String type;

    @OneToMany(mappedBy = "sensor")
    private List<Observation> observations;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Sensor() {
        this.observations = new ArrayList<>();
    }

    public Sensor(String name, String type) {
        this.name = name;
        this.type = type;
        this.observations = new ArrayList<>();
    }

    public int getId() {
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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
