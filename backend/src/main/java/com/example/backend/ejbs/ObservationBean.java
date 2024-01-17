package com.example.backend.ejbs;

import com.example.backend.entities.Observation;
import com.example.backend.entities.Sensor;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.websocket.WebsocketService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class ObservationBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private SensorBean sensorBean;


    public boolean exists(long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Observation p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(String type, String value, String unit, long sensorId) throws MyEntityNotFoundException {
        Sensor sensor = sensorBean.find(sensorId);

        Observation observation = new Observation(type, value, unit, sensor);
        entityManager.persist(observation);

        find(observation.getId());

        //send notification
        WebsocketService.sendNotification("joao", "New observation created: " + value);
        return observation.getId();
    }

    // Read
    public List<Observation> getAll() {
        return entityManager.createNamedQuery("getAllObservations", Observation.class).getResultList();
    }

    // Find
    public Observation find(long id) throws MyEntityNotFoundException {
        Observation observation = entityManager.find(Observation.class, id);
        if (observation == null) {
            throw new MyEntityNotFoundException("Observation with id " + id + " not found");
        }
        return observation;
    }

    // Update
    public void update(long id, String type, String value, String unit, long sensorId) throws MyEntityNotFoundException {
        Observation observation = find(id);

        if (type != null) {
            observation.setType(type);
        }
        if (value != null) {
            observation.setValue(value);
        }
        if (unit != null) {
            observation.setUnit(unit);
        }
        if (sensorId != 0) {
            Sensor sensor = sensorBean.find(sensorId);
            observation.setSensor(sensor);
        }

        entityManager.merge(observation);
    }

    // Delete
    public void delete(long id) throws MyEntityNotFoundException {
        Observation observation = find(id);
        entityManager.remove(observation);
    }
}
