package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.Observation;
import com.example.backend.entities.Customer;
import com.example.backend.exceptions.*;
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
public class CustomerBean {

    @PersistenceContext
    private EntityManager entityManager;
    private Hasher hasher;

    public Customer find(String username) throws MyEntityNotFoundException {
        Customer customer = entityManager.find(Customer.class, username);
        if (customer == null) {
            throw new MyEntityNotFoundException(" - Customer " + username +" not found");
        }
        return customer;
    }

    public Boolean exists(String username) {
        if (entityManager.find(Customer.class, username) == null)
            return true;
        else
            return false;
    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {

        if(exists(username))
        {
            try {
                hasher = new Hasher();
                Customer customer = new Customer(username, hasher.hash(password), name, email);
                entityManager.persist(customer);
                entityManager.flush(); // when using Hibernate, to force it to throw a ContraintViolationException, as in the JPA specification
                entityManager.persist(customer);
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
        Customer customer = entityManager.find(Customer.class, username);
        if (customer == null) {
            System.err.println("ERROR_CLIENT_NOT_FOUND: " + username);
            return;
        }
        entityManager.lock(customer, LockModeType.OPTIMISTIC);
        customer.setPassword(password);
        customer.setName(name);
        customer.setEmail(email);
    }

    public void delete(String username) throws MyEntityNotFoundException{
        entityManager.remove(find(username));
    }

    public List<Customer> getAllClients() {
        return entityManager.createNamedQuery("getAllClients", Customer.class).getResultList();
    }



    public Customer getClientOrders(String username) throws MyEntityNotFoundException {
        Customer customer = find(username);
        Hibernate.initialize(customer.getOrders());
        return this.find(username);
    }

    public Order getClientOrder(String username, Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        Order order = entityManager.find(Order.class, index);
        if(find(username).getUsername().equals(order.getClient().getUsername()))
            return entityManager.find(Order.class, index);
        else
            throw new NotAuthorizedException("This customer doesn't have any orders with the id " + index);
    }

    public List<Product> getClientOrderProducts(String username, Long index) throws MyEntityNotFoundException, NotAuthorizedException {
        Order order = entityManager.find(Order.class, index);
        if(find(username).getUsername().equals(order.getClient().getUsername())) {
            List<InventoryItem> physical = entityManager.find(Order.class, index).getPhysicalProducts();
            List<Product> products = new ArrayList<Product>();
            for (InventoryItem product : physical) {
                products.add(product.getProduct());
            }
            return products;
        }
        else
            throw new NotAuthorizedException("This customer doesn't have any orders with the id " + index);
    }

    public List<Observation> getClientOrderObservations(String username, Long index) throws MyEntityNotFoundException {
        find(username);
        return entityManager.find(Order.class, index).getObservations();
    }

//    public Customer getClientAlerts(String username) {
//        Customer customer = this.find(username);
//        if(customer != null)
//        {
//            Hibernate.initialize(customer.getAlerts());
//            return this.find(username);
//        }
//        return null;
//    }
}
