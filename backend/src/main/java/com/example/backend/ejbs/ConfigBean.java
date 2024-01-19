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
    private ManufacturerBean manufacturerBean;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private WarehouseOperatorBean warehouseOperatorBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private InventoryItemBean inventoryItemBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ObservationBean observationBean;
    @EJB
    private PackageBean packageBean;
    @EJB
    private ProductPackageBean productPackageBean;
    @EJB
    private TransportPackageBean transportPackageBean;
    @EJB
    private OrderBean orderBean;
    @EJB
    private AlertBean alertBean;


    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");

    @PostConstruct
    public void populateDB() {
        try {
            //#region Users
            manufacturerBean.create("maker1", "123", "maker1", "maker1@mail.pt");
            manufacturerBean.create("maker2", "123", "maker2", "maker2@mail.pt");
            manufacturerBean.create("maker3", "123", "maker3", "maker3@mail.pt");

            customerBean.create("joao", "123", "joao", "joao@mail.com");
            customerBean.create("bruno", "123", "bruno", "bruno@mail.com");

            warehouseOperatorBean.create("diogo", "123", "diogo", "diogo@mail.com");
            warehouseOperatorBean.create("marco", "123", "MARCO", "marco@mail.com");
            //#endregion

            //#region Products/PhysicalProducts
            productBean.create("Pizza", 10, "Pizza de queijo", 0.5, "Queijo", "maker1");
            productBean.create("Hamburger", 5, "Hamburger de carne", 0.5, "Carne", "maker1");
            productBean.create("Coca-Cola", 1, "Coca-Cola", 0.5, "Coca-Cola", "maker1");
            productBean.create("Batatas", 3, "Batatas", 0.7, "Batatas", "maker2");

            inventoryItemBean.create(1);
            inventoryItemBean.create(1);
            inventoryItemBean.create(1);
            inventoryItemBean.create(2);
            inventoryItemBean.create(2);
            inventoryItemBean.create(2);
            inventoryItemBean.create(3);
            inventoryItemBean.create(3);
            inventoryItemBean.create(3);
            inventoryItemBean.create(4);
            //#endregion

            //#region Sensors
            sensorBean.create("LT324", "Velocidade", "cm/s");
            sensorBean.create("RN324E", "Humidade","PPMw");
            sensorBean.create("TMPS234", "Temperatura", "ºC");
            //#endregion

            //#region ProductPackages/TransportPackage
            transportPackageBean.create(1, "Papel");
            transportPackageBean.create(1, "Plastico");
            transportPackageBean.create(3, "Metal");

            productPackageBean.create(1, "Papel");
            productPackageBean.create(1, "Plastico");
            productPackageBean.create(3, "Metal");
            //#endregion

            //#region Orders
            HashMap<Long, Integer> order1 = new HashMap<Long, Integer>();
            HashMap<Long, Integer> order2 = new HashMap<Long, Integer>();
            order1.put(1L, 1);
            order1.put(2L,1);
            order1.put(3L, 1);
            order2.put(1L, 2);
            order2.put(3L,1);
            orderBean.create("joao", "diogo", order1);
            orderBean.create("bruno", "marco", order1);
            orderBean.create("joao", "marco", order2);
            //orderBean.getAllProductsForOrder(1);
            //#endregion

            //#region Observations
            observationBean.create("20", 1);
            observationBean.create("10",  1);
            observationBean.create("40",  2);
            observationBean.create("342",  2);
            observationBean.create("23",  3);
            observationBean.create("45",  3);

            //#endregion

            //#region Alerts
            alertBean.create("joao", "Embalagem aberta");
            alertBean.create("bruno", "Temperatura elevada");
            //#endregion

            orderBean.update(3,3,2, "Sent");
        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
