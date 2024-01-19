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
    protected long id;
    protected int packageType;
    protected String material;

    @ManyToMany
    @JoinTable(
            name = "sensors_packages",
            joinColumns = @JoinColumn(name = "sensor_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "package_id"),

            }
    )
    private List<Sensor> sensors;


    public Package() {
        this.sensors = new ArrayList<>();
    }

    public Package(long id, int tipoEmbalagem, String material) {
        this.id = id;
        this.packageType = tipoEmbalagem;
        this.material = material;
        this.sensors = new ArrayList<>();
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

    public void addSensor(Sensor sensor) {
        if(sensor!= null)
            this.sensors.add(sensor);
    }

    public void removeSensor(Sensor sensor) {
        if(sensor!= null)
            this.sensors.remove(sensor);
    }
}
