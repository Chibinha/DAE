package com.example.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.CONTAINER)

public class ConfigBean {
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private WarehouseOperatorBean warehouseOperatorBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private TransportPackageBean transportPackageBean;
    @EJB
    private ProductPackageBean productPackageBean;
    @EJB
    private InventoryItemBean inventoryItemBean;
    @EJB
    private OrderBean orderBean;

    @EJB
    private ObservationBean observationBean;

    @EJB
    private AlertBean alertBean;


    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");


    @PostConstruct
    public void populateDB() {

        populate_Manufacturers();

        populate_Customers();

        populate_Operators();

        populate_Sensors();


        populate_Products();

        populate_TransportPackages();

        populate_ProductPackages();

        populate_InventoryItems();

        populate_Orders();

        populate_Observations();

        update_Orders();
    }


    private void update_Orders() {
        try {
            orderBean.update(1, 1, 2, "Preparar");
            orderBean.update(2, 2, 1, "Criada");
            orderBean.update(3, 3, 3, "Recebida");
            orderBean.update(4, 4, 4, "Preparar");
            orderBean.update(5, 5, 5, "Criada");
            orderBean.update(6, 6, 6, "Recebida");
            orderBean.update(7, 7, 7, "Preparar");
            orderBean.update(8, 8, 8, "Criada");
        } catch (Exception e) {
            logger.severe("Order Updates: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Observations() {
        try {
            observationBean.create("20", 1, 1);
            observationBean.create("10", 1, 1);
            observationBean.create("40", 2, 2);
            observationBean.create("342", 2, 2);
            observationBean.create("23", 3, 3);
            observationBean.create("45", 3, 3);
            observationBean.create("23", 4, 4);
            observationBean.create("45", 4, 4);
            observationBean.create("23", 5, 5);
            observationBean.create("45", 5, 5);
            observationBean.create("23", 6, 6);
            observationBean.create("45", 6, 6);
            observationBean.create("23", 7, 7);
            observationBean.create("45", 7, 7);
            observationBean.create("23", 8, 8);
            observationBean.create("45", 8, 8);
        } catch (Exception e) {
            logger.severe("Observations: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Orders() {
        try {
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
        } catch (Exception e) {
            logger.severe("Orders: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_InventoryItems() {
        try {
            List<Long> productPackageIds = new ArrayList<>();
            productPackageIds.add(1L);
            productPackageIds.add(2L);
            productPackageIds.add(3L);

            manufacturerBean.createInventoryItemList(1, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(2, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(3, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(4, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(5, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(6, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(7, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(8, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(9, 10, productPackageIds);
            manufacturerBean.createInventoryItemList(10, 10, productPackageIds);

        } catch (Exception e) {
            logger.severe("InventoryItems: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_ProductPackages() {
        try {
            productPackageBean.create("1", "Papel");
            productPackageBean.create("1", "Plastico");
            productPackageBean.create("3", "Metal");
            productPackageBean.create("4", "Madeira");
            productPackageBean.create("5", "Vidro");
            productPackageBean.create("6", "Cartão");
            productPackageBean.create("7", "Tecido");
            productPackageBean.create("8", "Cerâmica");
            productPackageBean.create("9", "Lata");
            productPackageBean.create("10", "Caixa");
            productPackageBean.create("11", "Saco");
        } catch (Exception e) {
            logger.severe("ProductPackages: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_TransportPackages() {
        try {
            transportPackageBean.create("1", "Papel");
            transportPackageBean.create("1", "Plastico");
            transportPackageBean.create("3", "Metal");
            transportPackageBean.create("4", "Madeira");
            transportPackageBean.create("5", "Vidro");
            transportPackageBean.create("6", "Cartão");
            transportPackageBean.create("7", "Tecido");
            transportPackageBean.create("8", "Cerâmica");
            transportPackageBean.create("9", "Lata");
            transportPackageBean.create("10", "Caixa");
        } catch (Exception e) {
            logger.severe("TransportPackages: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Sensors() {
        try {
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
        } catch (Exception e) {
            logger.severe("Sensors: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Products() {
        try {
            productBean.create("Pizza", 10, "Pizza de queijo", 0.5, "Queijo", "maker1");
            productBean.create("Hamburger", 5, "Hamburger de carne", 0.5, "Carne", "maker1");
            productBean.create("Coca-Cola", 1, "Coca-Cola", 0.5, "Coca-Cola", "maker1");
            productBean.create("Batatas", 3, "Batatas", 0.7, "Batatas", "maker2");
            productBean.create("Menu Familiar", 15, "Coca-Cola, Pizza de queijo, Hamburger de carne e Batatas", 3, "Various", "maker1");
            productBean.create("Pão de alho", 2, "Pão de alho", 0.4, "Pão, alho, manteiga e oregaos", "maker2");
            for (int i = 1; i <= 10; i++) {
                productBean.create("product" + i, i, "product" + i, i, "product" + i, "manufacturer" + i);
            }
        } catch (Exception e) {
            logger.severe("Products: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Operators() {
        try {
            warehouseOperatorBean.create("diogo", "123", "diogo", "diogo@mail.com");
            warehouseOperatorBean.create("marco", "123", "MARCO", "marco@mail.com");
            for (int i = 1; i <= 10; i++) {
                warehouseOperatorBean.create("warehouseOperator" + i, "123", "warehouseOperator" + i, "warehouseOperator" + i + "@mail.pt");
            }
        } catch (Exception e) {
            logger.severe("WarehouseOperators: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Customers() {
        try {
            customerBean.create("joao", "123", "joao", "joao@mail.com");
            customerBean.create("bruno", "123", "bruno", "bruno@mail.com");
            for (int i = 1; i <= 10; i++) {
                customerBean.create("customer" + i, "123", "customer" + i, "customer" + i + "@mail.pt");
            }
        } catch (Exception e) {
            logger.severe("Customers: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Manufacturers() {
        try {
            manufacturerBean.create("maker1", "123", "maker1", "maker1@mail.pt");
            manufacturerBean.create("maker2", "123", "maker2", "maker2@mail.pt");
            manufacturerBean.create("maker3", "123", "maker3", "maker3@mail.pt");
            for (int i = 4; i <= 10; i++) {
                manufacturerBean.create("maker" + i, "123", "maker" + i, "maker" + i + "@mail.pt");
            }

            for (int i = 1; i <= 10; i++) {
                manufacturerBean.create("manufacturer" + i, "123", "manufacturer" + i, "manufacturer" + i + "@mail.pt");
            }
        } catch (Exception e) {
            logger.severe("Manufacturers: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
