package com.example.backend.ejbs;

import com.example.backend.entities.Observation;
import com.example.backend.entities.Package;
import com.example.backend.entities.Sensor;
import com.example.backend.entities.TransportPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private PackageBean packageBean;

    public boolean exists(long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Sensor p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(String name, String type, String unit) throws MyEntityNotFoundException {
        Sensor sensor = new Sensor(name, type, unit);
        entityManager.persist(sensor);

        find(sensor.getId());

        return sensor.getId();
    }

    // Read
    public List<Sensor> getAll() {
        return entityManager.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    // Find
    public Sensor find(long id) throws MyEntityNotFoundException {
        Sensor sensor = entityManager.find(Sensor.class, id);
        if (sensor == null) {
            throw new MyEntityNotFoundException("Sensor with id " + id + " not found");
        }
        return sensor;
    }

    // Update
    public void update(long id, String name, String type) throws MyEntityNotFoundException {
        Sensor sensor = find(id);

        if (name != null) {
            sensor.setName(name);
        }
        if (type != null) {
            sensor.setType(type);
        }

        entityManager.merge(sensor);
    }

    // Delete
    public void delete(long id) throws MyEntityNotFoundException {
        Sensor sensor = find(id);
        entityManager.remove(sensor);
    }

    // Delete
    public List<Observation> getObservations(long id) throws MyEntityNotFoundException {
        Sensor sensor = find(id);
        return sensor.getObservations();
    }

    public void associateSensorToPackage(long id, long sensorId) throws MyEntityNotFoundException {
        Package aPackage = packageBean.find(id);
        Sensor sensor = this.find(sensorId);
        if(sensor.getCurrentPackage() != aPackage && aPackage.getCurrentSensor() != sensor)
        {
            aPackage.addSensor(sensor);
            sensor.addPackage(aPackage);
        }
    }
}
