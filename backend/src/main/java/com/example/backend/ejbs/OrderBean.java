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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ClientBean clientBean;
    @EJB
    private LineOperatorBean lineOperatorBean;

    //CRUFD
   /* public Order find(long idOrder) {
        return entityManager.find(Order.class, idOrder);
    }*/

    public void create(String orderType, String lineOperator_username, String client_username, Map<Long, Integer> products) throws MyConstraintViolationException, MyEntityNotFoundException {
        Client client = clientBean.find(client_username);
        LineOperator lineOperator = lineOperatorBean.find(lineOperator_username);
        if(client == null)
            throw new MyEntityNotFoundException("Client with username '" + client_username + "' not found");
        if(lineOperator == null)
            throw new MyEntityNotFoundException("Line Operator with username '" + lineOperator_username + "' not found");

        Order order = null;

        try {
            order = new Order(orderType, lineOperator, client);
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
            Hibernate.initialize(order.getProducts());
        }
        return order;
    }

    public List<PhysicalProduct> getAllProductsForOrder(long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if (order != null) {
            // Retrieve product IDs from the order's map
            List<Long> productIds = order.getProducts().keySet().stream().collect(Collectors.toList());

            // Retrieve products based on product IDs
            return entityManager.createQuery("SELECT p FROM Product p WHERE p.id IN :productIds", PhysicalProduct.class)
                    .setParameter("productIds", productIds)
                    .getResultList();
        }
        return Collections.emptyList();
    }
}

