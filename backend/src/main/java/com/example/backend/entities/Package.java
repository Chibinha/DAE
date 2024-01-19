package com.example.backend.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id" // JPQL
        )
})
@Table(
        name = "packages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
)
public abstract class Package implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected int packageType;
    protected String material;

    @ManyToMany(mappedBy = "packages")
    protected List<Sensor> sensors;

    //observations
    @OneToMany(mappedBy = "myPackage")
    protected List<Observation> observations;


    public Package() {
        this.sensors = new LinkedList<>();
        this.observations = new ArrayList<>();
    }

    public Package(int tipoEmbalagem, String material) {
        this.packageType = tipoEmbalagem;
        this.material = material;
        this.sensors = new LinkedList<>();
        this.observations = new ArrayList<>();
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

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }
}
