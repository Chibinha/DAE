package com.example.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
            for (int i = 1; i <= 10; i++) {
                manufacturerBean.create("manufacturer" + i, "123", "manufacturer" + i, "manufacturer" + i + "@mail.pt");
            }

            customerBean.create("joao", "123", "joao", "joao@mail.com");
            customerBean.create("bruno", "123", "bruno", "bruno@mail.com");
            for (int i = 1; i <= 10; i++) {
                customerBean.create("customer" + i, "123", "customer" + i, "customer" + i + "@mail.pt");
            }

            warehouseOperatorBean.create("diogo", "123", "diogo", "diogo@mail.com");
            warehouseOperatorBean.create("marco", "123", "MARCO", "marco@mail.com");
            for (int i = 1; i <= 10; i++) {
                warehouseOperatorBean.create("warehouseOperator" + i, "123", "warehouseOperator" + i, "warehouseOperator" + i + "@mail.pt");
            }
            //#endregion

            //#region Products/PhysicalProducts
            productBean.create("Pizza", 10, "Pizza de queijo", 0.5, "Queijo", "maker1");
            productBean.create("Hamburger", 5, "Hamburger de carne", 0.5, "Carne", "maker1");
            productBean.create("Coca-Cola", 1, "Coca-Cola", 0.5, "Coca-Cola", "maker1");
            productBean.create("Batatas", 3, "Batatas", 0.7, "Batatas", "maker2");
            productBean.create("Menu Familiar", 15, "Coca-Cola, Pizza de queijo, Hamburger de carne e Batatas", 3, "Various", "maker1");
            productBean.create("Pão de alho", 2, "Pão de alho", 0.4, "Pão, alho, manteiga e oregaos", "maker2");
            for (int i = 1; i <= 10; i++) {
                productBean.create("product" + i, 1, "product" + i, 1, "product" + i, "maker1");
            }

            inventoryItemBean.create(1, new ArrayList<Long>() {{
                add(1L);
                add(2L);
            }});

            inventoryItemBean.create(2, List.of(1L, 3L));

            inventoryItemBean.create(2, Arrays.asList(1L, 3L));
            inventoryItemBean.create(3, Arrays.asList(1L, 2L, 3L));
            inventoryItemBean.create(4, Arrays.asList(3L, 4L));
            inventoryItemBean.create(5, Arrays.asList(1L, 2L, 3L, 4L));
            inventoryItemBean.create(6, Arrays.asList(1L, 4L));
            inventoryItemBean.create(7, Arrays.asList(1L, 2L, 3L, 4L));
            inventoryItemBean.create(8, Arrays.asList(1L, 2L, 3L, 4L));
            inventoryItemBean.create(9, Arrays.asList(1L, 2L, 3L, 4L));
            inventoryItemBean.create(10, Arrays.asList(1L, 2L, 3L, 4L));


            //#endregion

            //#region Sensors
            sensorBean.create("LT324", "Velocidade", "cm/s");
            sensorBean.create("RN324E", "Humidade", "PPMw");
            sensorBean.create("TMPS234", "Temperatura", "ºC");
            sensorBean.create("AC234", "Aceleração", "m/s^2");
            sensorBean.create("BQ234", "Bateria", "V");
            sensorBean.create("CQ234", "Corrente", "A");
            sensorBean.create("PQ234", "Potência", "W");
            sensorBean.create("VQ234", "Tensão", "V");
            sensorBean.create("FQ234", "Frequência", "Hz");
            sensorBean.create("RQ234", "Resistência", "Ω");
            //#endregion

            //#region ProductPackages/TransportPackage
            transportPackageBean.create("1", "Papel");
            transportPackageBean.create("1", "Plastico");
            transportPackageBean.create("3", "Metal");
            transportPackageBean.create("4", "Madeira");
            transportPackageBean.create("5", "Vidro");
            transportPackageBean.create("6", "Cartão");
            transportPackageBean.create("7", "Tecido");
            transportPackageBean.create("8", "Cerâmica");

            productPackageBean.create("1", "Papel");
            productPackageBean.create("1", "Plastico");
            productPackageBean.create("3", "Metal");
            productPackageBean.create("4", "Madeira");
            productPackageBean.create("5", "Vidro");
            productPackageBean.create("6", "Cartão");
            productPackageBean.create("7", "Tecido");
            productPackageBean.create("8", "Cerâmica");

            //#endregion

            //#region Orders
            HashMap<Long, Integer> order1 = new HashMap<Long, Integer>();
            HashMap<Long, Integer> order2 = new HashMap<Long, Integer>();
            order1.put(1L, 1);
            order1.put(2L, 1);
            order1.put(3L, 1);
            order2.put(1L, 1);
            order2.put(3L, 1);
            orderBean.create("joao", order1);
            orderBean.create("bruno", order1);
            orderBean.create("joao", order2);
            orderBean.create("bruno", order2);
            orderBean.create("joao", order1);
            orderBean.create("bruno", order1);
            orderBean.create("joao", order2);
            orderBean.create("bruno", order2);
            //#endregion

            //#region Observations
            observationBean.create("20", 1, 1);
            observationBean.create("10", 1, 2);
            observationBean.create("40", 2, 3);
            observationBean.create("342", 2, 3);
            observationBean.create("23", 3, 3);
            observationBean.create("45", 3, 1);
            //#endregion

            orderBean.update(3, 2, 1, "Preparar");
            orderBean.update(2, 1, 3, "Criada");
            orderBean.update(1, 3, 2, "Recebida");

        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
