package com.example.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.HashMap;
import java.util.logging.Logger;

@Singleton
@Startup
public class ConfigBean {
    @EJB
    private MakerBean makerBean;
    @EJB
    private ClientBean clientBean;
    @EJB
    private LineOperatorBean lineOperatorBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private PhysicalProductBean physicalProductBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ObservationBean observationBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private ProductPackageBean productPackageBean;
    @EJB
    private TransportationPackageBean transportationPackageBean;
    @EJB
    private OrderBean orderBean;


    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try {
            //#region Users
            makerBean.create("maker1", "123", "maker1", "maker1@mail.pt");
            makerBean.create("maker2", "123", "maker2", "maker2@mail.pt");
            makerBean.create("maker3", "123", "maker3", "maker3@mail.pt");

            clientBean.create("joao", "123", "joao", "joao@mail.com");
            clientBean.create("bruno", "123", "bruno", "bruno@mail.com");

            lineOperatorBean.create("diogo", "123", "diogo", "diogo@mail.com");
            lineOperatorBean.create("marco", "123", "MARCO", "marco@mail.com");
            //#endregion

            //#region Products/PhysicalProducts
            productBean.create("Pizza", 10, "Pizza de queijo", 0.5, "Queijo", "maker1");
            productBean.create("Hamburger", 5, "Hamburger de carne", 0.5, "Carne", "maker1");
            productBean.create("Coca-Cola", 1, "Coca-Cola", 0.5, "Coca-Cola", "maker1");

            physicalProductBean.create("123456781", 1);
            physicalProductBean.create("187654322", 1);
            physicalProductBean.create("287654321", 2);
            physicalProductBean.create("223456782", 2);
            physicalProductBean.create("323456781", 3);
            physicalProductBean.create("387654322", 3);
            //#endregion

            //#region Sensors
            sensorBean.create("sensor1", "type1");
            sensorBean.create("sensor2", "type2");
            sensorBean.create("sensor3", "type3");
            //#endregion

            //#region Observations
            observationBean.create("type1", "value1", "unit1", 1);
            observationBean.create("type2", "value2", "unit2", 1);
            observationBean.create("type3", "value3", "unit3", 2);
            observationBean.create("type4", "value4", "unit4", 2);
            observationBean.create("type5", "value5", "unit5", 3);
            observationBean.create("type6", "value6", "unit6", 3);
            //#endregion


            //#region Packages
            //#endregion

            //#region Orders
            HashMap<Long, Integer> order1 = new HashMap<Long, Integer>();
            order1.put(1L, 1);
            order1.put(2L,1);
            order1.put(3L, 1);
            orderBean.create("joao", "diogo", order1);
            //orderBean.getAllProductsForOrder(1);
            //#endregion
        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
