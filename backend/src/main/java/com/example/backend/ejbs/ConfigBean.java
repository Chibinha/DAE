package com.example.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.*;

import java.util.*;
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

    public static String generateRandomFullName() {
        String[] firstNames = {"John", "Alice", "Bob", "Eva", "Michael", "Sophia", "David", "Emma"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson"};
        Random random = new Random();
        int randomFirstNameIndex = random.nextInt(firstNames.length);
        int randomLastNameIndex = random.nextInt(lastNames.length);
        String randomFirstName = firstNames[randomFirstNameIndex];
        String randomLastName = lastNames[randomLastNameIndex];
        String fullName = randomFirstName + " " + randomLastName;
        return fullName;
    }


    private void update_Orders() {
        try {
            Random random = new Random();
            orderBean.update(random.nextInt(4)+1, 1, 2, "Preparada");
            orderBean.update(random.nextInt(4)+1, 3, 3, "Recebida");
            orderBean.update(random.nextInt(4)+1, 4, 4, "Em transporte");
            orderBean.update(random.nextInt(4)+1, 5, 5, "Preparada");
        } catch (Exception e) {
            logger.severe("Order Updates: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Observations() {
        try {
            Random random = new Random();
            observationBean.create("20", 1, random.nextInt(4)+1);
            observationBean.create("10", 1, random.nextInt(4)+1);
            observationBean.create("40", 2, random.nextInt(4)+1);
            observationBean.create("342", 2,random.nextInt(4)+1);
            observationBean.create("23", 3, random.nextInt(4)+1);
            observationBean.create("45", 3, random.nextInt(4)+1);
            observationBean.create("23", 4, random.nextInt(4)+1);
            observationBean.create("45", 4, random.nextInt(4)+1);
            observationBean.create("23", 5, random.nextInt(4)+1);
            observationBean.create("45", 5, random.nextInt(4)+1);
            observationBean.create("23", 6, random.nextInt(4)+1);
            observationBean.create("45", 6, random.nextInt(4)+1);
            observationBean.create("23", 7, random.nextInt(4)+1);
            observationBean.create("45", 7, random.nextInt(4)+1);
            observationBean.create("23", 8, random.nextInt(4)+1);
            observationBean.create("45", 8, random.nextInt(4)+1);
        } catch (Exception e) {
            logger.severe("Observations: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_Orders() {
        try {
            HashMap<Long, Integer> order1 = new HashMap<Long, Integer>();
            HashMap<Long, Integer> order2 = new HashMap<Long, Integer>();
            HashMap<Long, Integer> order3 = new HashMap<Long, Integer>();
            HashMap<Long, Integer> order4 = new HashMap<Long, Integer>();
            order1.put(1L, 1);
            order1.put(5L, 4);
            order1.put(3L, 1);
            order2.put(4L, 2);
            order2.put(2L, 1);
            order3.put(7L, 1);
            order3.put(9L, 2);
            order4.put(8L, 1);
            order4.put(6L, 3);
            order4.put(10L, 1);
            orderBean.create("joao", order1);
            orderBean.create("bruno", order2);
            orderBean.create("joao", order3);
            orderBean.create("bruno", order4);
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
            Random random = new Random();

            manufacturerBean.createInventoryItemList(1, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(2, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(3, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(4, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(5, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(6, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(7, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(8, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(9, random.nextInt(15) + 1, productPackageIds);
            manufacturerBean.createInventoryItemList(10,random.nextInt(15) + 1, productPackageIds);

        } catch (Exception e) {
            logger.severe("InventoryItems: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_ProductPackages() {
        try {
            productPackageBean.create("Primária", "Papel");
            productPackageBean.create("Secundária", "Plastico");
            productPackageBean.create("Terciária", "Metal");
            productPackageBean.create("Primária", "Madeira");
            productPackageBean.create("Secundária", "Vidro");
            productPackageBean.create("Primária", "Cartão");
            productPackageBean.create("Secundária", "Tecido");
            productPackageBean.create("Primária", "Cerâmica");
            productPackageBean.create("Secundária", "Lata");
            productPackageBean.create("Primária", "Caixa");
            productPackageBean.create("Terciária", "Saco");
        } catch (Exception e) {
            logger.severe("ProductPackages: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }


    private void populate_TransportPackages() {
        try {
            transportPackageBean.create("Primária", "Papel");
            transportPackageBean.create("Primária", "Plastico");
            transportPackageBean.create("Secundária", "Metal");
            transportPackageBean.create("Primária", "Madeira");
            transportPackageBean.create("Secundária", "Vidro");
            transportPackageBean.create("Primária", "Cartão");
            transportPackageBean.create("Terciária", "Tecido");
            transportPackageBean.create("Primária", "Cerâmica");
            transportPackageBean.create("Terciária", "Lata");
            transportPackageBean.create("Secundária", "Caixa");
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
            for (int i = 1; i <= 4; i++) {
                String randomName = generateRandomFullName();
                warehouseOperatorBean.create(
                    "operator" + i,
                    "123",
                    randomName,
                    "operator" + i + "@mail.pt"
                );
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
                String randomName = generateRandomFullName();
                customerBean.create("customer" + i, "123", randomName, "customer" + i + "@mail.pt");
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
                String randomName = generateRandomFullName();
                manufacturerBean.create("maker" + i, "123", randomName, "maker" + i + "@mail.pt");
            }

            for (int i = 1; i <= 10; i++) {
                String randomName = generateRandomFullName();
                manufacturerBean.create("manufacturer" + i, "123", randomName, "manufacturer" + i + "@mail.pt");
            }
        } catch (Exception e) {
            logger.severe("Manufacturers: " + e.getClass().getCanonicalName() + e.getMessage());
        }
    }
}
