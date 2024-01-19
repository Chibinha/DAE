package com.example.backend.ejbs;

import com.example.backend.entities.Alert;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.User;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;

import java.util.List;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;
    public User find(String username) {
        return em.find(User.class, username);
    }
    public User findOrFail(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }
    public boolean canLogin(String username, String password) {
        var user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        var user = find(username);
        if (!user.getPassword().equals(hasher.hash(oldPassword)))
            return false;

        user.setPassword(hasher.hash(newPassword));
        return true;
    }

    public List<Alert> getAlerts(String username) throws MyEntityNotFoundException {
        var user = find(username);
        return em.createNamedQuery("getUserAlerts", Alert.class)
            .setParameter("user", user)
            .getResultList();
    }
}
