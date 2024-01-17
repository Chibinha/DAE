package com.example.backend.ejbs;

import com.example.backend.entities.Client;
import com.example.backend.entities.LineOperator;
import com.example.backend.entities.Order;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;

import java.util.ArrayList;
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
    @EJB
    private ProductBean productBean;

    //CRUFD
   /* public Order find(long idOrder) {
        return entityManager.find(Order.class, idOrder);
    }*/

    //usernames client and lineop
    //Receives a json payload with product ids and quantities
    public void create(String usernameClient, String usernameLineOp, Map<Long, Integer> products) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        Client client = clientBean.find(usernameClient);
        LineOperator lineOperator = lineOperatorBean.find(usernameLineOp);
        List<PhysicalProduct> physicalProducts = new ArrayList<>();
        double totalPrice = 0;

        // Retrieve PhysicalProducts for each product ID
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            // Retrieve PhysicalProducts for the product ID
            List<PhysicalProduct> productPhysicalProducts = productBean.getListPhysicalProducts(productId);

            if (productPhysicalProducts == null || productPhysicalProducts.isEmpty()) {
                throw new MyEntityNotFoundException("Physical products not found for product ID: " + productId);
            }

            // Add PhysicalProducts to the list
            for (int i = 0; i < quantity; i++) {
                physicalProducts.add(productPhysicalProducts.get(i));
                totalPrice += productPhysicalProducts.get(i).getProduct().getPrice();
            }
        }

        Order order = new Order("order", totalPrice, lineOperator, client, physicalProducts);
        entityManager.persist(order);
    }

    public List<Order> getAll() {
        return (List<Order>) entityManager.createNamedQuery("getAllOrders").getResultList();
    }

    public Order find(long idOrder) throws MyEntityNotFoundException {
        Order order = entityManager.find(Order.class, idOrder);

        if (order == null) {
            throw new MyEntityNotFoundException("Order with id " + idOrder + " not found");
        }
        return order;
    }

//    public List<PhysicalProduct> getAllProductsForOrder(long orderId) {
//        Order order = entityManager.find(Order.class, orderId);
//        if (order != null) {
//            // Retrieve product IDs from the order's map
//            List<Long> productIds = order.getProducts().keySet().stream().collect(Collectors.toList());
//
//            // Retrieve products based on product IDs
//            return entityManager.createQuery("SELECT p FROM Product p WHERE p.id IN :productIds", PhysicalProduct.class)
//                    .setParameter("productIds", productIds)
//                    .getResultList();
//        }
//        return Collections.emptyList();
//    }
}

