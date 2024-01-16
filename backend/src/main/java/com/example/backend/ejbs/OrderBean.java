package com.example.backend.ejbs;

import com.example.backend.entities.Client;
import com.example.backend.entities.LineOperator;
import com.example.backend.entities.Order;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Map;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ClientBean clientBean;

    //CRUFD
   /* public Order find(long idOrder) {
        return entityManager.find(Order.class, idOrder);
    }*/

    public void create(String orderType, String lineOperator_username, String client_username, Map<Long, Integer> products) throws MyConstraintViolationException, MyEntityNotFoundException {
        Client client = clientBean.find(client_username);
        if(client == null)
            throw new MyEntityNotFoundException("Client with username '" + client_username + "' not found");

        Order order = null;

        try {
            order = new Order(orderType, lineOperator_username, client);
            entityManager.persist(order);
            entityManager.flush();
        }
        catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            PhysicalProduct physicalProduct = entityManager.find(PhysicalProduct.class, entry.getKey());
            if (physicalProduct == null) {
                throw new MyEntityNotFoundException(
                        "PhysicalProduct with id '" + entry.getKey() + "' not found"
                );
            }
            order.addProductQuantity(physicalProduct, entry.getValue());
        }
    }

    public List<Order> getAllOrders() {
        return (List<Order>) entityManager.createNamedQuery("getAllOrders").getResultList();
    }

    public Order findOrder(long idOrder) {
        Order order = entityManager.find(Order.class, idOrder);
        if (order != null) {
            // Ensure the products are initialized
            Hibernate.initialize(order.getProductQuantities());
        }
        return order;
    }
}

