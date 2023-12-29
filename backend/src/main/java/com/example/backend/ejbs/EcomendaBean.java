package com.example.backend.ejbs;

import com.example.backend.entities.Encomendas;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Date;

@Stateless
public class EcomendaBean {

    @PersistenceContext
    EntityManager em;

    public void create(int idEncomenda, Date dataEncomenda, String consumidorFinal, String operadorLogistica, String estadoEncomenda ){
        Encomendas ecomendas = new Encomendas(idEncomenda, dataEncomenda, consumidorFinal, operadorLogistica, estadoEncomenda);
        em.persist(ecomendas);
    }

}
