package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.entities.Package;
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

    @EJB
    private OrderBean orderBean;

    @EJB
    private AlertBean alertBean;


    public boolean exists(long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Observation p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(String value, long sensorId, long orderId) throws MyEntityNotFoundException {
        Sensor sensor = sensorBean.find(sensorId);
        Order order = orderBean.find(orderId);
        Observation observation = new Observation(value, sensor, order);
        entityManager.persist(observation);

        find(observation.getId());

        // Check if the value is out of range and create an alert if needed
        if (!isValueInRange(value, sensor)) {
            // Fetch the user associated with the order
            User user = fetchUserFromOrder(order);

            // Send WebSocket notification dynamically
            if (user != null) {
                WebsocketService.sendNotification(user.getUsername(), "Sensor value out of range: " + value);
            }

            // Create an alert
            alertBean.create(user.getUsername(), "Sensor value out of range: " + value);
        }

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
    public void update(long id, String value, long sensorId) throws MyEntityNotFoundException {
        Observation observation = find(id);

        if (value != null) {
            observation.setValue(value);
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

    private boolean isValueInRange(String value, Sensor sensor) {
        // Implement your logic to check if the value is within the sensor's range
        // For example, you can compare the value with min and max range values of the sensor.
        // Replace the following lines with your specific logic.

        if (sensor.getType().equals("Velocidade")) {
            // Example logic for Humidade sensor
            return Integer.parseInt(value) >= 5 && Integer.parseInt(value) <= 200;
        } else if (sensor.getType().equals("Humidade")) {
            // Example logic for Velocidade sensor
            return Integer.parseInt(value) >= 40 && Integer.parseInt(value) <= 100;
        } else if (sensor.getType().equals("Temperatura")) {
            // Example logic for Temperatura sensor
            return Integer.parseInt(value) >= -6 && Integer.parseInt(value) <= 50;
        } else {
            // Handle other sensor types if needed
            return false;
        }
    }

    private User fetchUserFromOrder(Order order) {
        // Your logic to fetch the user associated with the order
        // For example, check if the order has a customer or a warehouse operator
        if (order.getClient() != null) {
            return order.getClient();
        } else if (order.getLineOperator() != null) {
            return order.getLineOperator();
        }

        return null;
    }
}
