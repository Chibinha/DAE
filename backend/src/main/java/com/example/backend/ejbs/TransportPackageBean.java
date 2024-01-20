package com.example.backend.ejbs;

import com.example.backend.dtos.TransportPackageDTO;
import com.example.backend.entities.Order;
import com.example.backend.entities.TransportPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
public class TransportPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    @EJB
    private OrderBean orderBean;

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(t.id) FROM TransportPackage t WHERE t.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }
    public long create(int packageType, String material) throws MyEntityNotFoundException {
        TransportPackage transportPackage = new TransportPackage(packageType, material);
        entityManager.persist(transportPackage);

        find(transportPackage.getId());

        return transportPackage.getId();
    }

    public TransportPackage find(long id) throws MyEntityNotFoundException {
        TransportPackage transportPackage = entityManager.find(TransportPackage.class, id);
        if (transportPackage == null) {
            throw new MyEntityNotFoundException("Package with id " + id + " not found");
        }
        return transportPackage;
    }

    public List<TransportPackage> getAll() {
        return entityManager.createNamedQuery("getAllTransportationPackages", TransportPackage.class).getResultList();
    }

    public long update(long id, TransportPackageDTO transportPackageDTO) throws MyEntityNotFoundException {
        TransportPackage transportPackage = find(id);

        if (transportPackageDTO.getPackageType() != 0) {
            transportPackage.setPackageType(transportPackageDTO.getPackageType());
        }
        if (transportPackageDTO.getMaterial() != null) {
            transportPackage.setMaterial(transportPackageDTO.getMaterial());
        }

        entityManager.merge(transportPackage);
        return transportPackage.getId();
    }

    public void delete(long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }
}
