package com.example.backend.ejbs;

import com.example.backend.entities.Order;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.entities.ProductPackage;
import com.example.backend.entities.TransportationPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class TransportationPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, int packageType, String material, Order order) {
        TransportationPackage transportationPackage = new TransportationPackage(id, packageType, material, order);
        this.entityManager.persist(transportationPackage);
    }

    public TransportationPackage find(long id) {
        return entityManager.find(TransportationPackage.class, id);
    }

    public List<TransportationPackage> getAll() {
        return entityManager.createNamedQuery("getAllTransportationPackages", TransportationPackage.class).getResultList();
    }

    public void associateTransportationPackageToOrder(long id, long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if(order == null)
            return;
        TransportationPackage transportationPackage = entityManager.find(TransportationPackage.class, id);
        if(transportationPackage == null)
            return;
        else {
            order.addTransportationPackage(transportationPackage);
        }
    }

    public void dissociateTransportationPackageFromOrder(long id, long orderId) {
        Order order = entityManager.find(Order.class, orderId);
        if(order == null)
            return;
        TransportationPackage transportationPackage = entityManager.find(TransportationPackage.class, id);
        if(transportationPackage == null)
            return;
        else {
            order.removeTransportationPackage(transportationPackage);
        }
    }

    public void removeTransportationPackage(long id){
        TransportationPackage transportationPackage = entityManager.find(TransportationPackage.class, id);
        if (transportationPackage != null) {
            entityManager.remove(transportationPackage);
        }
    }


    public TransportationPackage update(long id, int packageType, String material) throws MyEntityNotFoundException {
        TransportationPackage transportationPackage = entityManager.find(TransportationPackage.class, id);
        if(transportationPackage == null)
            throw new MyEntityNotFoundException("Transportation Package with id '" + id + "' not found");

        entityManager.lock(transportationPackage, LockModeType.OPTIMISTIC);
        transportationPackage.setPackageType(packageType);
        transportationPackage.setMaterial(material);

        return transportationPackage;
    }
}
