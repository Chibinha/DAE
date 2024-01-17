package com.example.backend.ejbs;

import com.example.backend.entities.Order;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.entities.ProductPackage;
import com.example.backend.entities.TransportationPackage;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class TransportationPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

//    @EJB
//    private OrderBean orderBean;

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(t.id) FROM TransportationPackage t WHERE t.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public TransportationPackage create(long id, int packageType, String material, long order_id) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(id)) {
            throw new MyEntityExistsException(
                    "Package with id '" + id + "' already exists"
            );
        }
        Order order = entityManager.find(Order.class, order_id);
        if (order == null) {
            throw new MyEntityNotFoundException(
                    "Order with id '" + order_id + "' not found"
            );
        }

        TransportationPackage transportationPackage = null;

        try {
            transportationPackage = new TransportationPackage(id, packageType, material, order);
            entityManager.persist(transportationPackage);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return transportationPackage;
    }

    public TransportationPackage find(long id) throws MyEntityNotFoundException {
        TransportationPackage transportationPackage = entityManager.find(TransportationPackage.class, id);
        if (transportationPackage == null) {
            throw new MyEntityNotFoundException("Package with id " + id + " not found");
        }
        return transportationPackage;
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
            //order.addTransportationPackage(transportationPackage);
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
            //order.removeTransportationPackage(transportationPackage);
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
