package com.example.backend.ejbs;

import com.example.backend.entities.LineOperator;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;

import java.util.List;
@Stateless
public class LineOperatorBean {

    @PersistenceContext
    private EntityManager entityManager;
    private Hasher hasher;

    public LineOperator find(long id) throws MyEntityNotFoundException {
        LineOperator lineOperator = entityManager.find(LineOperator.class, id);
        if (lineOperator == null) {
            throw new MyEntityNotFoundException("Client with id " + id + " not found");
        }
        return lineOperator;
    }

    public LineOperator find(String username) {
        return entityManager.find(LineOperator.class, username);

    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException {

        if(this.find(username)== null)
        {
            try {
                hasher = new Hasher();
                LineOperator lineOperator = new LineOperator(username, hasher.hash(password), name, email);
                entityManager.persist(lineOperator);
                entityManager.flush(); // when using Hibernate, to force it to throw a ContraintViolationException, as in the JPA specification
                entityManager.persist(lineOperator);
            }
            catch (ConstraintViolationException e) {
                throw new MyConstraintViolationException(e);
            }
        }
        else {
            throw new MyEntityExistsException(" ERROR -  The username [" + username + "] is already in use!!!");
        }
    }

    public void update(String username, String password, String name, String email) {
        LineOperator lineOperator = entityManager.find(LineOperator.class, username);
        if (lineOperator == null) {
            System.err.println("ERROR_CLIENT_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(lineOperator, LockModeType.OPTIMISTIC);
        lineOperator.setPassword(password);
        lineOperator.setName(name);
        lineOperator.setEmail(email);
    }

    public void delete(long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }

    public List<LineOperator> getAllLineOperators() {
        return entityManager.createNamedQuery("getAllLineOperators", LineOperator.class).getResultList();
    }


    public LineOperator getLineOperatorOrders(String username) {
        LineOperator lineOperator = this.find(username);
        if(lineOperator != null)
        {
//            Hibernate.initialize(lineOperator.getOrders());
            return this.find(username);
        }
        return null;
    }
}
