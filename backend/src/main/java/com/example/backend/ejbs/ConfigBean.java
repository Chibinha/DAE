package com.example.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Stateless;

import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private ProductBean productBean;
    @EJB
    private PhysicalProductBean physicalProductBean;
    //private ClientBean clientBean;
    //private OrderBean orderBean;
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try {
            productBean.create("Pizza", "Pizza de queijo", 0.5, "Queijo");
            productBean.create("Hamburger", "Hamburger de carne", 0.5, "Carne");
            productBean.create("Coca-Cola", "Coca-Cola", 0.5, "Coca-Cola");

            // public long create(String serialNumber, long productId)
            physicalProductBean.create("123456781", 1);
            physicalProductBean.create("187654322", 1);
            physicalProductBean.create("287654321", 2);
            physicalProductBean.create("223456782", 2);
            physicalProductBean.create("323456781", 3);
            physicalProductBean.create("387654322", 3);


            //clientBean.create("joao", "123", "joao", "joao@mail.com");
            //clientBean.create("bruno", "123", "bruno", "bruno@mail.com");
            //orderBean.create(1, date.toString(2024.01.11), "Alfredo", "Joaquim", "Delivered");

        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
