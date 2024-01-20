
package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.Observation;
import com.example.backend.entities.WarehouseOperator;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.exceptions.NotAuthorizedException;
import com.example.backend.security.Hasher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;

import java.util.ArrayList;
import java.util.List;
@Stateless
public class WarehouseOperatorBean {

    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private OrderBean orderBean;
    @EJB
    private AlertBean alertBean;
    private Hasher hasher;

    public WarehouseOperator find(String username) throws MyEntityNotFoundException {
        WarehouseOperator warehouseOperator = entityManager.find(WarehouseOperator.class, username);
        if (warehouseOperator == null) {
            throw new MyEntityNotFoundException(" - Line Operator " + username +" not found");
        }
        return warehouseOperator;
    }

    public Boolean exists(String username) {
        return entityManager.find(WarehouseOperator.class, username) == null;
    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {

        if(exists(username))
        {
            try {
                hasher = new Hasher();
                WarehouseOperator warehouseOperator = new WarehouseOperator(username, hasher.hash(password), name, email);
                entityManager.persist(warehouseOperator);
                entityManager.flush(); // when using Hibernate, to force it to throw a ContraintViolationException, as in the JPA specification
                entityManager.persist(warehouseOperator);
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
    public List<WarehouseOperator> getAll() {
        return entityManager.createNamedQuery("getAllLineOperators", WarehouseOperator.class).getResultList();
    }

    public void update(String username, String password, String name, String email) {
        WarehouseOperator warehouseOperator = entityManager.find(WarehouseOperator.class, username);
        if (warehouseOperator == null) {
            System.err.println("ERROR_LINEOPERATOR_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(warehouseOperator, LockModeType.OPTIMISTIC);
        warehouseOperator.setPassword(password);
        warehouseOperator.setName(name);
        warehouseOperator.setEmail(email);
    }

    public void delete(String username) throws MyEntityNotFoundException{
        entityManager.remove(find(username));
    }

    public List<WarehouseOperator> getAllLineOperators() {
        return entityManager.createNamedQuery("getAllLineOperators", WarehouseOperator.class).getResultList();
    }


    public WarehouseOperator getLineOperatorOrders(String username) throws MyEntityNotFoundException {
        WarehouseOperator warehouseOperator = find(username);
        Hibernate.initialize(warehouseOperator.getOrders());
        return this.find(username);
    }

    public Order getLineOperatorOrder(String username, Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        Order order = entityManager.find(Order.class, index);
        if(find(username).getUsername().equals(order.getLineOperator().getUsername()))
            return entityManager.find(Order.class, index);
        else
            throw new NotAuthorizedException("This Line Operator doesn't have any orders with the id " + index);
    }

    public List<Product> getLineOperatorOrderProducts(String username, Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        Order order = entityManager.find(Order.class, index);
        if(find(username).getUsername().equals(order.getLineOperator().getUsername())) {
            List<InventoryItem> physical = entityManager.find(Order.class, index).getInventoryItems();
            List<Product> products = new ArrayList<Product>();
            for (InventoryItem product : physical) {
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

    public void updateOrder(int index,int packageId, int sensorId, String status) throws MyConstraintViolationException, MyEntityNotFoundException, MyEntityExistsException {
        orderBean.update(index,packageId, sensorId, status);
    }
}
