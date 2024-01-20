package com.example.backend.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "packages")
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id" // JPQL
        )
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Package implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String type;
    protected String material;

    @ManyToMany(mappedBy = "packages")
    protected List<Sensor> sensors;

    public Package() {
        this.sensors = new LinkedList<>();
    }

    public Package(String type, String material) {
        this.type = type;
        this.material = material;
        this.sensors = new LinkedList<>();
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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    public void addSensor(Sensor sensor) {
        if (!sensors.contains(sensor)) {
            sensors.add(sensor);
        }
    }

    public Sensor getCurrentSensor() {
        if(!sensors.isEmpty()) {
            return sensors.get(sensors.size() - 1);
        }
        return null;
    }

    public List<Observation> getAllObservations() {
        List<Observation> observations = new ArrayList<>();
        for(Sensor sensor : sensors )
        {
            if(sensor.getCurrentPackage() == this)
            {
                observations.addAll(sensor.getObservations());
            }
        }
        return observations;
    }
}
