package com.example.backend.ejbs;

import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

import java.util.ArrayList;
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
            populateUsers();
            populateProducts();
            populateSensors();
            populatePackages();
//            populateOrders();
            populateAlerts();

//            orderBean.update(3,3,1, "Preparar");
//            orderBean.update(2,1,2, "Criada");
//            orderBean.update(1,2,3, "Recebida");

//            orderBean.update(1, 2, 1, "Pending");
//            orderBean.update(2, 1, 2, "Shipped");
//            orderBean.update(3, 3, 2, "Sent");
//            orderBean.update(4, 2, 1, "Delivered");
//            orderBean.update(5, 4, 2, "Processing");
//            orderBean.update(6, 3, 1, "Shipped");
//            orderBean.update(7, 1, 2, "Sent");
//            orderBean.update(8, 2, 3, "Pending");
//            orderBean.update(9, 3, 1, "Processing");
//            orderBean.update(10, 4, 2, "Delivered");

        } catch (Exception e) {
            logger.severe(e.getClass().getCanonicalName() + e.getMessage());
        }
    }

    private void populateAlerts() throws MyEntityNotFoundException {


//        alertBean.create("maker1", "Embalagem danificada");
//        alertBean.create("maker2", "Temperatura elevada");
//        alertBean.create("maker3", "Embalagem aberta");
//        alertBean.create("maker4", "Produto fora do prazo de validade");
//        alertBean.create("maker5", "Hamburguer fora da temperatura ideal");
//        alertBean.create("maker6", "Fuga de humidade detectada");
//        alertBean.create("maker7", "Sensor de temperatura com defeito");
//        alertBean.create("maker8", "Pedido atrasado");
//        alertBean.create("maker9", "Sensor de velocidade com defeito");
//        alertBean.create("maker10", "Embalagem aberta");
//
//        alertBean.create("customer1", "Embalagem aberta");
//        alertBean.create("customer2", "Temperatura elevada");
//        alertBean.create("customer3", "Embalagem danificada");
//        alertBean.create("customer4", "Produto fora do prazo de validade");
//        alertBean.create("customer5", "Hamburguer fora da temperatura ideal");
//        alertBean.create("customer6", "Fuga de humidade detectada");
//        alertBean.create("customer7", "Sensor de temperatura com defeito");
//        alertBean.create("customer8", "Pedido atrasado");
//        alertBean.create("customer9", "Sensor de velocidade com defeito");
//        alertBean.create("customer10", "Embalagem aberta");
//
//        alertBean.create("operator1", "Embalagem aberta");
//        alertBean.create("operator2", "Temperatura elevada");
//        alertBean.create("operator3", "Embalagem danificada");
//        alertBean.create("operator4", "Produto fora do prazo de validade");
//        alertBean.create("operator5", "Hamburguer fora da temperatura ideal");
//        alertBean.create("operator6", "Fuga de humidade detectada");
//        alertBean.create("operator7", "Sensor de temperatura com defeito");
//        alertBean.create("operator8", "Pedido atrasado");
//        alertBean.create("operator9", "Sensor de velocidade com defeito");
//        alertBean.create("operator10", "Embalagem aberta");
    }

    private void populateOrders() throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
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

//        HashMap<Long, Integer> order1 = new HashMap<Long, Integer>();
//        order1.put(1L, 1);
//        order1.put(2L, 1);
//        order1.put(3L, 1);
//        orderBean.create("customer1", "operator5", order1);
//
//        HashMap<Long, Integer> order2 = new HashMap<Long, Integer>();
//        order2.put(1L, 2);
//        order2.put(3L, 1);
//        orderBean.create("customer1", "operator7", order2);
//
//        HashMap<Long, Integer> order3 = new HashMap<Long, Integer>();
//        order3.put(4L, 3);
//        order3.put(6L, 1);
//        order3.put(8L, 2);
//        orderBean.create("customer2", "operator3", order3);
//
//        HashMap<Long, Integer> order4 = new HashMap<Long, Integer>();
//        order4.put(5L, 1);
//        order4.put(7L, 2);
//        order4.put(9L, 3);
//        orderBean.create("customer2", "operator2", order4);
//
//        HashMap<Long, Integer> order5 = new HashMap<Long, Integer>();
//        order5.put(10L, 4);
//        order5.put(1L, 1);
//        order5.put(3L, 2);
//        orderBean.create("customer3", "operator2", order5);
//
//        HashMap<Long, Integer> order6 = new HashMap<Long, Integer>();
//        order6.put(2L, 2);
//        order6.put(4L, 1);
//        order6.put(6L, 3);
//        orderBean.create("operator1", "operator1", order6);
//
//        HashMap<Long, Integer> order7 = new HashMap<Long, Integer>();
//        order7.put(1L, 1);
//        order7.put(5L, 2);
//        order7.put(9L, 1);
//        orderBean.create("operator1", "operator3", order7);
//
//        HashMap<Long, Integer> order8 = new HashMap<Long, Integer>();
//        order8.put(3L, 2);
//        order8.put(7L, 1);
//        order8.put(10L, 3);
//        orderBean.create("operator2", "operator2", order8);
//
//        HashMap<Long, Integer> order9 = new HashMap<Long, Integer>();
//        order9.put(8L, 1);
//        order9.put(4L, 2);
//        order9.put(5L, 3);
//        orderBean.create("operator2", "operator1", order9);
//
//        HashMap<Long, Integer> order10 = new HashMap<Long, Integer>();
//        order10.put(9L, 1);
//        order10.put(10L, 2);
//        order10.put(2L, 3);
//        orderBean.create("operator3", "operator1", order10);
    }

    private void populateSensors() throws MyEntityNotFoundException {
        sensorBean.create("LT324", "Velocidade", "cm/s");
        sensorBean.create("RN324E", "Humidade", "PPMw");
        sensorBean.create("TMPS234", "Temperatura", "ºC");
        sensorBean.create("Sensor4", "Property4", "Unit4");
        sensorBean.create("Sensor5", "Property5", "Unit5");
        sensorBean.create("Sensor6", "Property6", "Unit6");
        sensorBean.create("Sensor7", "Property7", "Unit7");
        sensorBean.create("Sensor8", "Property8", "Unit8");
        sensorBean.create("Sensor9", "Property9", "Unit9");
        sensorBean.create("Sensor10", "Property10", "Unit10");
        sensorBean.create("Sensor11", "Property11", "Unit11");
        sensorBean.create("Sensor12", "Property12", "Unit12");
        sensorBean.create("Sensor13", "Property13", "Unit13");
        //#region Observations
        observationBean.create("20", 1);
        observationBean.create("10", 1);
        observationBean.create("30", 1);
        observationBean.create("40", 2);
        observationBean.create("50", 2);
        observationBean.create("342", 2);
        observationBean.create("23", 3);
        observationBean.create("34", 3);
        observationBean.create("45", 3);
        observationBean.create("30", 4);
        observationBean.create("20", 4);
        observationBean.create("10", 4);
        observationBean.create("10", 5);
        observationBean.create("30", 5);
        observationBean.create("40", 5);
        observationBean.create("50", 6);
        observationBean.create("342", 6);
        observationBean.create("23", 6);
        observationBean.create("34", 7);
        observationBean.create("45", 7);
        observationBean.create("30", 7);
        observationBean.create("20", 8);
        observationBean.create("10", 8);
        observationBean.create("30", 8);
        observationBean.create("40", 9);
        observationBean.create("50", 9);
        observationBean.create("342", 9);
        observationBean.create("23", 10);
        observationBean.create("34", 10);
        observationBean.create("45", 10);
    }

    private void populateProducts() throws MyEntityNotFoundException {
        productBean.create("Pizza", 10, "Pizza de queijo", 0.5, "Queijo", "maker1");
        productBean.create("Hamburger", 5, "Hamburger de carne", 0.5, "Carne", "maker1");
        productBean.create("Coca-Cola", 1, "Coca-Cola", 0.5, "Coca-Cola", "maker1");
        productBean.create("Batatas", 3, "Batatas", 0.7, "Batatas", "maker2");

        inventoryItemBean.create(1, generateProductIds(1));
        inventoryItemBean.create(2, generateProductIds(2));
        inventoryItemBean.create(3, generateProductIds(3));


//        productBean.create("Product1", 10, "Description1", 0.5, "Ingredient1", "manufacturer1");
//        productBean.create("Product2", 5, "Description2", 0.4, "Ingredient2", "manufacturer1");
//        productBean.create("Product3", 6, "Description3", 0.3, "Ingredient3", "manufacturer1");
//        productBean.create("Product4", 9, "Description4", 0.7, "Ingredient4", "manufacturer2");
//        productBean.create("Product5", 8, "Description5", 0.8, "Ingredient5", "manufacturer2");
//        productBean.create("Product6", 12, "Description6", 0.6, "Ingredient6", "manufacturer2");
//        productBean.create("Product7", 7, "Description7", 0.9, "Ingredient7", "manufacturer3");
//        productBean.create("Product8", 15, "Description8", 0.4, "Ingredient8", "manufacturer3");
//        productBean.create("Product9", 20, "Description9", 0.3, "Ingredient9", "manufacturer4");
//        productBean.create("Product10", 4, "Description10", 0.6, "Ingredient10", "manufacturer4");
//        productBean.create("Product11", 3, "Description11", 0.7, "Ingredient11", "manufacturer5");
//        productBean.create("Product12", 2, "Description12", 0.8, "Ingredient12", "manufacturer5");
//        productBean.create("Product13", 1, "Description13", 0.9, "Ingredient13", "manufacturer6");
//        productBean.create("Product14", 2, "Description14", 0.4, "Ingredient14", "manufacturer6");
//        productBean.create("Product15", 3, "Description15", 0.3, "Ingredient15", "manufacturer7");
//        productBean.create("Product16", 4, "Description16", 0.6, "Ingredient16", "manufacturer7");
//        productBean.create("Product17", 5, "Description17", 0.7, "Ingredient17", "manufacturer8");
//        productBean.create("Product18", 6, "Description18", 0.8, "Ingredient18", "manufacturer8");
//        productBean.create("Product19", 7, "Description19", 0.9, "Ingredient19", "manufacturer9");
//        productBean.create("Product20", 8, "Description20", 0.4, "Ingredient20", "manufacturer9");
//        productBean.create("Product21", 9, "Description21", 0.3, "Ingredient21", "manufacturer10");
//        productBean.create("Product22", 10, "Description22", 0.6, "Ingredient22", "manufacturer10");
//
//        int numberOfProducts = 10;
//        int numberOfProductIds = 6;
//
//        for (int i = 1; i <= numberOfProducts; i++) {
//            List<Long> productIds = new ArrayList<>();
//
//            for (int j = 1; j <= numberOfProductIds; j++) {
//                long associatedProductId = (i + j - 1) % 10 + 1;
//                productIds.add(associatedProductId);
//            }
//
//            inventoryItemBean.create(i, productIds);
//        }
    }

    private void populateUsers() throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        manufacturerBean.create("maker1", "123", "maker1", "maker1@mail.pt");
        manufacturerBean.create("maker2", "123", "maker2", "maker2@mail.pt");
        manufacturerBean.create("maker3", "123", "maker3", "maker3@mail.pt");

        customerBean.create("joao", "123", "joao", "joao@mail.com");
        customerBean.create("bruno", "123", "bruno", "bruno@mail.com");

        warehouseOperatorBean.create("diogo", "123", "diogo", "diogo@mail.com");
        warehouseOperatorBean.create("marco", "123", "MARCO", "marco@mail.com");

//        manufacturerBean.create("manufacturer1", "123", "manufacturer1", "manufacturer1@mail.pt");
//        manufacturerBean.create("manufacturer2", "123", "manufacturer2", "manufacturer2@mail.pt");
//        manufacturerBean.create("manufacturer3", "123", "manufacturer3", "manufacturer3@mail.pt");
//        manufacturerBean.create("manufacturer4", "123", "manufacturer4", "manufacturer4@mail.pt");
//        manufacturerBean.create("manufacturer5", "123", "manufacturer5", "manufacturer5@mail.pt");
//        manufacturerBean.create("manufacturer6", "123", "manufacturer6", "manufacturer6@mail.pt");
//        manufacturerBean.create("manufacturer7", "123", "manufacturer7", "manufacturer7@mail.pt");
//        manufacturerBean.create("manufacturer8", "123", "manufacturer8", "manufacturer8@mail.pt");
//        manufacturerBean.create("manufacturer9", "123", "manufacturer9", "manufacturer9@mail.pt");
//        manufacturerBean.create("manufacturer10", "123", "manufacturer10", "manufacturer10@mail.pt");
//
//
//        customerBean.create("customer1", "123", "customer1", "customer1@mail.pt");
//        customerBean.create("customer2", "123", "customer2", "customer2@mail.pt");
//        customerBean.create("customer3", "123", "customer3", "customer3@mail.pt");
//        customerBean.create("customer4", "123", "customer4", "customer4@mail.pt");
//        customerBean.create("customer5", "123", "customer5", "customer5@mail.pt");
//        customerBean.create("customer6", "123", "customer6", "customer6@mail.pt");
//        customerBean.create("customer7", "123", "customer7", "customer7@mail.pt");
//        customerBean.create("customer8", "123", "customer8", "customer8@mail.pt");
//        customerBean.create("customer9", "123", "customer9", "customer9@mail.pt");
//        customerBean.create("customer10", "123", "customer10", "customer10@mail.pt");
//
//        warehouseOperatorBean.create("operator1", "123", "operator1", "operator1@mail.com");
//        warehouseOperatorBean.create("operator2", "123", "operator2", "operator2@mail.com");
//        warehouseOperatorBean.create("operator3", "123", "operator3", "operator3@mail.com");
//        warehouseOperatorBean.create("operator4", "123", "operator4", "operator4@mail.com");
//        warehouseOperatorBean.create("operator5", "123", "operator5", "operator5@mail.com");
//        warehouseOperatorBean.create("operator6", "123", "operator6", "operator6@mail.com");
//        warehouseOperatorBean.create("operator7", "123", "operator7", "operator7@mail.com");
//        warehouseOperatorBean.create("operator8", "123", "operator8", "operator8@mail.com");
//        warehouseOperatorBean.create("operator9", "123", "operator9", "operator9@mail.com");
//        warehouseOperatorBean.create("operator10", "123", "operator10", "operator10@mail.com");
    }

    private void populatePackages() throws MyEntityNotFoundException {
        transportPackageBean.create("transport1", "transportPapel");
        transportPackageBean.create("transport2", "transportPlastico");
        transportPackageBean.create("transport3", "transportMetal");
        transportPackageBean.create("transport4", "transportAluminio");
        transportPackageBean.create("transport5", "transportCartao");
        transportPackageBean.create("transport6", "transportSaco");
        transportPackageBean.create("transport7", "transportCaixa");
        transportPackageBean.create("transport8", "transportGarrafa");
        transportPackageBean.create("transport9", "transportLata");
        transportPackageBean.create("transport10", "transportPalha");


        productPackageBean.create("productPackage1", "productPackagePapel");
        productPackageBean.create("productPackage2", "productPackagePlastico");
        productPackageBean.create("productPackage3", "productPackageMetal");
        productPackageBean.create("productPackage4", "productPackageAluminio");
        productPackageBean.create("productPackage5", "productPackageCartao");
        productPackageBean.create("productPackage6", "productPackageSaco");
        productPackageBean.create("productPackage7", "productPackageCaixa");
        productPackageBean.create("productPackage8", "productPackageGarrafa");
        productPackageBean.create("productPackage9", "productPackageLata");
        productPackageBean.create("productPackage10", "productPackagePalha");
    }

    private List<Long> generateProductIds(int productId) {


        List<Long> productIds = new ArrayList<>();
        int numberOfProductIds = 2; // Adjust the number based on your actual requirement

//        for (int i = 1; i <= numberOfProductIds; i++) {
//            // Calculate the product ID based on the current iteration and the total number of products
//            long associatedProductId = (productId + i - 1) % 10 + 1;
//            productIds.add(associatedProductId);
//        }



        return productIds;
    }
}
