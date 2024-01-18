package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.exceptions.NotAuthorizedException;
import com.example.backend.security.Hasher;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
@Stateless
public class LineOperatorBean {

    @PersistenceContext
    private EntityManager entityManager;
    private Hasher hasher;

    public LineOperator find(String username) throws MyEntityNotFoundException {
        LineOperator lineOperator = entityManager.find(LineOperator.class, username);
        if (lineOperator == null) {
            throw new MyEntityNotFoundException(" - Line Operator " + username +" not found");
        }
        return lineOperator;
    }

    public Boolean exists(String username) {
        return entityManager.find(LineOperator.class, username) == null;
    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {

        if(exists(username))
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

    //getAll
    public List<LineOperator> getAll() {
        return entityManager.createNamedQuery("getAllLineOperators", LineOperator.class).getResultList();
    }

    public void update(String username, String password, String name, String email) {
        LineOperator lineOperator = entityManager.find(LineOperator.class, username);
        if (lineOperator == null) {
            System.err.println("ERROR_LINEOPERATOR_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(lineOperator, LockModeType.OPTIMISTIC);
        lineOperator.setPassword(password);
        lineOperator.setName(name);
        lineOperator.setEmail(email);
    }

    public void delete(String username) throws MyEntityNotFoundException{
        entityManager.remove(find(username));
    }

    public List<LineOperator> getAllLineOperators() {
        return entityManager.createNamedQuery("getAllLineOperators", LineOperator.class).getResultList();
    }


    public LineOperator getLineOperatorOrders(String username) throws MyEntityNotFoundException {
        LineOperator lineOperator = find(username);
        Hibernate.initialize(lineOperator.getOrders());
        return this.find(username);
    }

    public Order getLineOperatorOrder(String username, Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        Order order = entityManager.find(Order.class, index);
        if(find(username).getUsername().equals(order.getClient().getUsername()))
            return entityManager.find(Order.class, index);
        else
            throw new NotAuthorizedException("This Line Operator doesn't have any orders with the id " + index);
    }

    public List<Product> getLineOperatorOrderProducts(String username, Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        Order order = entityManager.find(Order.class, index);
        if(find(username).getUsername().equals(order.getClient().getUsername())) {
            List<PhysicalProduct> physical = entityManager.find(Order.class, index).getPhysicalProducts();
            List<Product> products = new ArrayList<Product>();
            for (PhysicalProduct product : physical) {
                products.add(product.getProduct());
            }
            return products;
        }
        else
            throw new NotAuthorizedException("This Line Operator doesn't have any orders with the id " + index);
    }

    public List<Observation> getLineOperatorOrderObservations(String username, Long index) throws MyEntityNotFoundException {
        find(username);
        return entityManager.find(Order.class, index).getObservations();
    }
}
