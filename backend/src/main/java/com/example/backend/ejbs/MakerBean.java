package com.example.backend.ejbs;

import com.example.backend.entities.Maker;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

@Stateless
public class MakerBean {
    @PersistenceContext
    private EntityManager entityManager;
    private Hasher hasher;

    public Maker find(long id) throws MyEntityNotFoundException {
        Maker maker = entityManager.find(Maker.class, id);
        if (maker == null) {
            throw new MyEntityNotFoundException("Maker with id " + id + " not found");
        }
        return maker;
    }

    public Maker find(String username) throws MyEntityNotFoundException {
        Maker maker = entityManager.find(Maker.class, username);
        if (maker == null) {
            throw new MyEntityNotFoundException("Maker with username " + username + " not found");
        }
        return maker;
    }

    public void create(String username, String password, String name, String email) {
        hasher = new Hasher();
        Maker maker = new Maker(username, hasher.hash(password), name, email);
        entityManager.persist(maker);
    }
}