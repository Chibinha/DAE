package com.example.backend.ejbs;

import com.example.backend.entities.Alert;
import com.example.backend.entities.Product;
import com.example.backend.entities.ProductPackage;
import com.example.backend.entities.User;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class AlertBean {
    @PersistenceContext
    private EntityManager entityManager;

    public long create(String username, String description)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {

        User user = entityManager.find(User.class, username);

        Alert alert = new Alert(user, description);

        entityManager.persist(alert);

        return alert.getId();
    }

    public Alert find(long id) throws MyEntityNotFoundException {
        Alert alert = entityManager.find(Alert.class, id);
        if (alert == null) {
            throw new MyEntityNotFoundException("Alert with id " + id + " not found");
        }
        return alert;
    }


}
