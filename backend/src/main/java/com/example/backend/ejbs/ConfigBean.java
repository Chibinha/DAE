package com.example.backend.ejbs;

import com.example.backend.entities.Maker;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private ProductBean productBean;
    @EJB
    private PhysicalProductBean physicalProductBean;
    @EJB
    private ClientBean clientBean;
    @EJB
    private MakerBean makerBean;
    @EJB
    private LineOperatorBean lineOperatorBean;
    //private OrderBean orderBean;
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try {
            //String username, String password, String name, String email
            makerBean.create("maker1", "123", "maker1", "maker1@mail.pt");
            makerBean.create("maker2", "123", "maker2", "maker2@mail.pt");
            makerBean.create("maker3", "123", "maker3", "maker3@mail.pt");

            productBean.create("Pizza", 10, "Pizza de queijo", 0.5, "Queijo", "maker1");
            productBean.create("Hamburger", 5, "Hamburger de carne", 0.5, "Carne", "maker2");
            productBean.create("Coca-Cola", 1, "Coca-Cola", 0.5, "Coca-Cola", "maker3");


            // public long create(String serialNumber, long productId)
            physicalProductBean.create("123456781", 1);
            physicalProductBean.create("187654322", 1);
            physicalProductBean.create("287654321", 2);
            physicalProductBean.create("223456782", 2);
            physicalProductBean.create("323456781", 3);
            physicalProductBean.create("387654322", 3);


            clientBean.create("joao", "123", "joao", "joao@mail.com");
            clientBean.create("bruno", "123", "bruno", "bruno@mail.com");
            lineOperatorBean.create("diogo", "123", "diogo", "diogo@mail.com");
            lineOperatorBean.create("marco", "123", "MARCO", "marco@mail.com");

            //orderBean.create(1, date.toString(2024.01.11), "Alfredo", "Joaquim", "Delivered");

        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
