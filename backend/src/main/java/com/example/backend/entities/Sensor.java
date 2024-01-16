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
    private String type;

    @OneToMany(mappedBy = "sensor")
    private List<Observation> observations;

    public Sensor() {
        this.observations = new ArrayList<>();
    }

    public Sensor(int id, String type) {
        this.id = id;
        this.type = type;
        this.observations = new ArrayList<>();
    }
}
