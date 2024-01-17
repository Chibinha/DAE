package com.example.backend.ejbs;

import com.example.backend.entities.Alert;
import com.example.backend.entities.User;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.websocket.WebsocketService;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class AlertBean {
    @PersistenceContext
    private EntityManager entityManager;

    // CRUD
    // Create
    public long create(String username, String description) throws MyEntityNotFoundException {
        User user = entityManager.find(User.class, username);

        Alert alert = new Alert(user, description);
        entityManager.persist(alert);

        find(alert.getId());

        // Send WebSocket notification
        WebsocketService.sendNotification(username, description);

        return alert.getId();
    }

    // Read
    public List<Alert> getUserAlerts(String username) throws MyEntityNotFoundException {
        User user = entityManager.find(User.class, username);
        if (user == null) {
            throw new MyEntityNotFoundException("User with username " + username + " not found");
        }
        return entityManager.createNamedQuery("getUserAlerts", Alert.class).setParameter("user", user).getResultList();
    }

    // Find
    public Alert find(long id) throws MyEntityNotFoundException {
        Alert alert = entityManager.find(Alert.class, id);
        if (alert == null) {
            throw new MyEntityNotFoundException("Alert with id " + id + " not found");
        }
        return alert;
    }

    // Delete
    public void delete(long id) throws MyEntityNotFoundException {
        Alert alert = find(id);
        entityManager.remove(alert);
    }
}
