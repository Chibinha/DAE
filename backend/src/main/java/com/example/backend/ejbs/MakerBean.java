package com.example.backend.ejbs;

import com.example.backend.entities.Maker;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class MakerBean {
    @PersistenceContext
    private EntityManager entityManager;
    private Hasher hasher;

    // CRUD
    // Create
    public void create(String username, String password, String name, String email) {
        hasher = new Hasher();
        Maker maker = new Maker(username, hasher.hash(password), name, email);
        entityManager.persist(maker);
    }

    // Read
    public List<Maker> getAll() {
        return entityManager.createNamedQuery("getAllMakers", Maker.class).getResultList();
    }

    // Find
    public Maker find(String username) throws MyEntityNotFoundException {
        Maker maker = entityManager.find(Maker.class, username);
        if (maker == null) {
            throw new MyEntityNotFoundException("Maker with username " + username + " not found");
        }
        return maker;
    }

    // Update
    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException {
        Maker maker = find(username);

        if (password != null) {
            hasher = new Hasher();
            maker.setPassword(hasher.hash(password));
        }
        if (name != null) {
            maker.setName(name);
        }
        if (email != null) {
            maker.setEmail(email);
        }

        entityManager.merge(maker);
    }

    // Delete
    public void delete(String username) throws MyEntityNotFoundException {
        Maker maker = find(username);
        entityManager.remove(maker);
    }
}