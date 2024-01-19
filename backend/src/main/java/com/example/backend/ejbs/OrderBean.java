package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.entities.Package;
import com.example.backend.exceptions.MyConstraintViolationException;

import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
public class OrderBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ClientBean clientBean;
    @EJB
    private LineOperatorBean lineOperatorBean;
    @EJB
    private TransportationPackageBean transportationPackageBean;
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

    //usernames client and lineop
    //Receives a json payload with product ids and quantities
    public void create(String usernameClient, String usernameLineOp, Map<Long, Integer> products) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        Client client = clientBean.find(usernameClient);
        LineOperator lineOperator = lineOperatorBean.find(usernameLineOp);
        List<PhysicalProduct> physicalProducts = new ArrayList<>();
        double totalPrice = 0;

        Order order = new Order(totalPrice, lineOperator, client, physicalProducts);

        // Retrieve PhysicalProducts for each product ID
        for (Map.Entry<Long, Integer> entry : products.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            // Retrieve PhysicalProducts for the product ID
            List<PhysicalProduct> productPhysicalProducts = productBean.getListPhysicalProducts(productId);

            if (productPhysicalProducts == null || productPhysicalProducts.isEmpty()) {
                throw new MyEntityNotFoundException("Physical products not found for product ID: " + productId);
            }

            // Add PhysicalProducts to the list
            for (int i = 0; i < quantity; i++) {
                PhysicalProduct productToAdd = productPhysicalProducts.get(i);
                physicalProducts.add(productPhysicalProducts.get(i));
                productToAdd.setOrder(order);
                totalPrice += productPhysicalProducts.get(i).getProduct().getPrice();
            }
        }
        order.setPhysicalProducts(physicalProducts);
        order.setTotalPrice(totalPrice);
        entityManager.persist(order);
    }

    public void update(int id, TransportationPackage packageOrder, Sensor sensor, String status) throws MyEntityNotFoundException, MyEntityExistsException, MyConstraintViolationException {
        Order order = find(id);
        associateTransportationPackageToOrder(packageOrder.getId(), id);
        //associateSensorToPackage(sensor.getId(), packageOrder.getId());
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

    public void associateTransportationPackageToOrder(long id, int packageId) throws MyEntityNotFoundException {
        Order order = find(id);
        TransportationPackage transportationPackage = transportationPackageBean.find(packageId);
        if(transportationPackage.getCurrentOrder() != order)
        {
            order.addPackage(transportationPackage);
            transportationPackage.addOrder(order);
        }
    }

    public void associateSensorToPackage(long id, int sensorId) throws MyEntityNotFoundException {
        Package aPackage = packageBean.find(id);
        Sensor sensor = sensorBean.find(sensorId);
        if(sensor.getCurrentPackage() != aPackage)
        {
            //aPackage.addSensor(sensor);
            sensor.addPackage(aPackage);
        }
    }
}

