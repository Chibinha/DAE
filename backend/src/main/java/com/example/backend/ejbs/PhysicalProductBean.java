package com.example.backend.ejbs;

import com.example.backend.entities.PhysicalProduct;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class PhysicalProductBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM PhysicalProduct p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRFUD

    // Create
    public long create(String serialNumber, long productId) throws MyEntityNotFoundException{
        Product product = productBean.find(productId);

        PhysicalProduct physicalProduct = new PhysicalProduct(product, serialNumber);
        entityManager.persist(physicalProduct);

        return physicalProduct.getId();
    }

    // Read
    public List<PhysicalProduct> getAll() {
        return entityManager.createNamedQuery("getAllPhysicalProducts", PhysicalProduct.class).getResultList();
    }

    // Find
    public PhysicalProduct find(long id) throws MyEntityNotFoundException {
        PhysicalProduct physicalProduct = entityManager.find(PhysicalProduct.class, id);
        if (physicalProduct == null) {
            throw new MyEntityNotFoundException("PhysicalProduct with id " + id + " not found");
        }
        return physicalProduct;
    }

    // Update
    public void update(long id, String serialNumber) throws MyEntityNotFoundException {
        PhysicalProduct physicalProduct = find(id);
        if (serialNumber != null) {
            physicalProduct.setSerialNumber(serialNumber);
        }

        entityManager.merge(physicalProduct);
    }
    // Delete
    public void delete(long id) throws MyEntityNotFoundException {
        entityManager.remove(find(id));
    }
}
