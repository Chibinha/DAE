package com.example.backend.entities.associations;

import com.example.backend.entities.Package;
import com.example.backend.entities.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "sensors_packages")
public class SensorPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sensor_id")
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package aPackage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "associationDate", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp associationDate;

    public SensorPackage(Long id, Sensor sensor, Package aPackage, Timestamp associationDate) {
        this.id = id;
        this.sensor = sensor;
        this.aPackage = aPackage;
        this.associationDate = associationDate;
    }

    public SensorPackage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public Package getaPackage() {
        return aPackage;
    }

    public void setaPackage(Package aPackage) {
        this.aPackage = aPackage;
    }

    public Timestamp getAssociationDate() {
        return associationDate;
    }

    public void setAssociationDate(Timestamp associationDate) {
        this.associationDate = associationDate;
    }
}
