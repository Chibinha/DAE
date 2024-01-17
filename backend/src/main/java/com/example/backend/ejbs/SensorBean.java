package com.example.backend.ejbs;

import com.example.backend.entities.Sensor;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class SensorBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Sensor p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(String name, String type) throws MyEntityNotFoundException {
        Sensor sensor = new Sensor(name, type);
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
}
