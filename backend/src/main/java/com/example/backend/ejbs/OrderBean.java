package com.example.backend.ejbs;

import com.example.backend.entities.Order;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;

@Stateless
public class OrderBean {

    @PersistenceContext
    EntityManager em;

    /*public void create(long idOrder, Date orderDate, String orderType, String materialType, String product ){
        Order order = new Order(idOrder, orderDate, orderType, materialType, product);
        em.persist(order);
    }
*/
}
