package com.example.backend.ejbs;

import com.example.backend.dtos.ProductPackageDTO;
import com.example.backend.dtos.TransportationPackageDTO;
import com.example.backend.entities.*;
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

    @EJB
    private OrderBean orderBean;

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(t.id) FROM TransportationPackage t WHERE t.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public long create(int packageType, String material) throws MyEntityNotFoundException {
        TransportationPackage transportationPackage = new TransportationPackage(packageType, material);
        entityManager.persist(transportationPackage);

        find(transportationPackage.getId());

        return transportationPackage.getId();
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

    public long update(long id, TransportationPackageDTO transportationPackageDTO) throws MyEntityNotFoundException {
        TransportationPackage transportationPackage = find(id);

        if (transportationPackageDTO.getPackageType() != 0) {
            transportationPackage.setPackageType(transportationPackageDTO.getPackageType());
        }
        if (transportationPackageDTO.getMaterial() != null) {
            transportationPackage.setMaterial(transportationPackageDTO.getMaterial());
        }

        entityManager.merge(transportationPackage);
        return transportationPackage.getId();
    }

    public void delete(long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }
}
