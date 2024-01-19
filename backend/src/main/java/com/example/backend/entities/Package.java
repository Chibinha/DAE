package com.example.backend.entities;

import com.example.backend.entities.Observation;
import com.example.backend.entities.Sensor;
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
    protected int packageType;
    protected String material;

    @ManyToMany(mappedBy = "packages")
    protected List<Sensor> sensors;

    public Package() {
        this.sensors = new LinkedList<>();
    }

    public Package(int tipoEmbalagem, String material) {
        this.packageType = tipoEmbalagem;
        this.material = material;
        this.sensors = new LinkedList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int tipoEmbalagem) {
        this.packageType = tipoEmbalagem;
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

    public List<Observation> getAllObservations() {
        List<Observation> observations = new ArrayList<>();
        for(Sensor sensor : sensors )
        {
            observations.addAll(sensor.getObservations());
        }
        return observations;
    }
}
