package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.entities.Package;
import com.example.backend.entities.TransportPackage;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Sensor;
import com.example.backend.entities.Customer;
import com.example.backend.entities.WarehouseOperator;
import com.example.backend.exceptions.MyConstraintViolationException;

import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.*;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private CustomerBean customerBean;
    @EJB
    private WarehouseOperatorBean warehouseOperatorBean;
    @EJB
    private TransportPackageBean transportPackageBean;
    @EJB
    private SensorBean sensorBean;

    @EJB
    private PackageBean packageBean;
    @EJB
    private ProductBean productBean;

    //CRUFD
   /* public Order find(long idOrder) {
        return entityManager.find(Order.class, idOrder);
    }*/

    //usernames customer and lineop
    //Receives a json payload with product ids and quantities
    public long create(String usernameClient, Map<Long, Integer> products) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        Customer customer = customerBean.find(usernameClient);
        List<WarehouseOperator> warehouseOperators = warehouseOperatorBean.getAll();
        List<InventoryItem> inventoryItems = new ArrayList<>();
        double totalPrice = 0;

        // Randomly select a WarehouseOperator
        Random random = new Random();
        int randomIndex = random.nextInt(warehouseOperators.size());
        WarehouseOperator chosenOperator = warehouseOperators.get(randomIndex);

        Order order = new Order(totalPrice, chosenOperator, customer, inventoryItems);

        // Retrieve PhysicalProducts for each product ID
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            // Retrieve PhysicalProducts for the product ID
            List<InventoryItem> productInventoryItems = productBean.getListPhysicalProducts(productId);

            if (productInventoryItems == null || productInventoryItems.isEmpty()) {
                throw new MyEntityNotFoundException("Physical products not found for product ID: " + productId);
            }

            // Add PhysicalProducts to the list
            for (int i = 0; i < quantity; i++) {
                InventoryItem productToAdd = productInventoryItems.get(i);
                inventoryItems.add(productToAdd);
                productToAdd.setOrder(order);
                totalPrice += productToAdd.getProduct().getPrice();
            }
        }

        order.setPhysicalProducts(inventoryItems);
        order.setTotalPrice(totalPrice);
        entityManager.persist(order);
        return order.getId();
    }

    public void update(int orderId, int packageId, int sensorId, String status) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        Order order = find(orderId);
        associateTransportationPackageToOrder(order.getId(), packageId);
        associateSensorToPackage(packageId, sensorId);
        order.setStatus(status);
    }

    public List<Order> getAll() {
        return (List<Order>) entityManager.createNamedQuery("getAllOrders").getResultList();
    }

    public Order find(long idOrder) throws MyEntityNotFoundException {
        Order order = entityManager.find(Order.class, idOrder);

        if (order == null) {
            throw new MyEntityNotFoundException("Order with id " + idOrder + " not found");
        }
        return order;
    }

    public void associateTransportationPackageToOrder(long id, long packageId) throws MyEntityNotFoundException {
        Order order = find(id);
        TransportPackage transportPackage = transportPackageBean.find(packageId);
        if(transportPackage.getCurrentOrder() != order)
        {
            order.addPackage(transportPackage);
            transportPackage.addOrder(order);
        }
    }

    public void associateSensorToPackage(long id, long sensorId) throws MyEntityNotFoundException {
        Package aPackage = packageBean.find(id);
        Sensor sensor = sensorBean.find(sensorId);
        if(sensor.getCurrentPackage() != aPackage)
        {
            //aPackage.addSensor(sensor);
            sensor.addPackage(aPackage);
        }
    }

    public boolean exists(long id) throws MyEntityNotFoundException  {
        Query query = entityManager.createQuery("SELECT COUNT(o) FROM Order o WHERE o.id = :id");
        query.setParameter("id", id);
        if ((long) query.getSingleResult() > 0L) {
            return true;
        } else {
            throw new MyEntityNotFoundException("Order with id " + id + " not found");
        }
    }
}

