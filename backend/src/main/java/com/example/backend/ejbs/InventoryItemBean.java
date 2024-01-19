package com.example.backend.ejbs;

import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class InventoryItemBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;
    @EJB
    private ManufacturerBean manufacturerBean;
//    @EJB
//    private OrderBean orderBean;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM InventoryItem p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(long productId) throws MyEntityNotFoundException{
        Product product = productBean.find(productId);

        InventoryItem inventoryItem = new InventoryItem(product);
        entityManager.persist(inventoryItem);

        find(inventoryItem.getId());

        return inventoryItem.getId();
    }

    // Read
    public List<InventoryItem> getAll() {
        return entityManager.createNamedQuery("getAllPhysicalProducts", InventoryItem.class).getResultList();
    }

    // Find
    public InventoryItem find(long id) throws MyEntityNotFoundException {
        InventoryItem inventoryItem = entityManager.find(InventoryItem.class, id);
        if (inventoryItem == null) {
            throw new MyEntityNotFoundException("InventoryItem with id " + id + " not found");
        }
        return inventoryItem;
    }

    // Update
    public long update(long id) throws MyEntityNotFoundException {
        InventoryItem inventoryItem = find(id);

        entityManager.merge(inventoryItem);
        return inventoryItem.getId();
    }
    // Delete
    public void delete(long id) throws MyEntityNotFoundException {
        InventoryItem inventoryItem = find(id);
        inventoryItem.getProduct().removePhysicalProduct(inventoryItem);
        entityManager.remove(find(id));
    }
}
