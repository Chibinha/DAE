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

    public void create(int idEncomenda, Date dataEncomenda, String consumidorFinal, String operadorLogistica, String estadoEncomenda ){
        Order ecomendas = new Order(idEncomenda, dataEncomenda, consumidorFinal, operadorLogistica, estadoEncomenda);
        em.persist(ecomendas);
    }

}
