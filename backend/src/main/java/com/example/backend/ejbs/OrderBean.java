//package com.example.backend.ejbs;
//
//import com.example.backend.entities.Client;
//import com.example.backend.entities.Order;
//import com.example.backend.entities.PhysicalProduct;
//import com.example.backend.exceptions.MyConstraintViolationException;
//import com.example.backend.exceptions.MyEntityExistsException;
//import com.example.backend.exceptions.MyEntityNotFoundException;
//import com.example.backend.security.Hasher;
//import jakarta.ejb.Stateless;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.validation.ConstraintViolationException;
//
//import java.util.List;
//import java.util.Map;
//
//@Stateless
//public class OrderBean {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public Order find(long idOrder) {
//        return entityManager.find(Order.class, idOrder);
//    }
//
//    // needs to receive the product ids to purchase and the quantities
//    public void create(String orderType, String materialType, String client_username, Map<Long, Integer> products) throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
//        Client client = entityManager.find(Client.class, client_username);
//        if (client == null) {
//            throw new MyEntityNotFoundException(
//                    "Client with username '" + client_username + "' not found"
//            );
//        }
//
//        Order order = null;
//
//        try {
//            order = new Order(orderType, materialType, client);
//            entityManager.persist(order);
//        } catch (ConstraintViolationException e) {
//            throw new MyConstraintViolationException(e);
//        }
//
//        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
//            PhysicalProduct physicalProduct = entityManager.find(PhysicalProduct.class, entry.getKey());
//            if (physicalProduct == null) {
//                throw new MyEntityNotFoundException(
//                        "PhysicalProduct with id '" + entry.getKey() + "' not found"
//                );
//            }
//            order.addPhysicalProduct(physicalProduct, entry.getValue());
//        }
//    }
//}