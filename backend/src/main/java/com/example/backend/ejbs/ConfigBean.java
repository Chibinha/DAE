package com.example.backend.ejbs;

import jakarta.ejb.EJB;

import java.util.logging.Logger;

public class ConfigBean {
    @EJB
    //private ClientBean clientBean;
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    public void populateDB() {
        try {
            //clientBean.create("joao", "123", "joao", "joao@mail.com");
            //clientBean.create("bruno", "123", "bruno", "bruno@mail.com");
            //ecomendaBean.create(1, date.toString(2024.01.11), "Alfredo", "Joaquim", "Delivered");

        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
