package com.example.backend.entities;

import jakarta.persistence.*;
import org.glassfish.jaxb.runtime.v2.runtime.reflect.Lister;

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
    @ManyToMany(mappedBy = "sensors")
    private List<Package> packages;

    public Sensor() {
        this.observations = new ArrayList<>();
        this.packages = new ArrayList<>();
    }

    public Sensor(String name, String type, String unit) {
        this.name = name;
        this.type = type;
        this.unit = unit;
        this.observations = new ArrayList<>();
        this.packages = new ArrayList<>();
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

    public List<Package> getPackages() {
        return packages;
    }
    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }



    public Package getCurrentPackage() {
        if(!packages.isEmpty())
            return packages.get(packages.size() - 1);
        return null;
    }

    public void addPackage(Package aPackage) {
        if(aPackage!= null)
            this.packages.add(aPackage);
    }

    public void removePackage(Package aPackage) {
        if(aPackage!= null)
            this.packages.remove(aPackage);
    }
}
