package com.example.backend.ejbs;

import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Manufacturer;
import com.example.backend.entities.Product;
import com.example.backend.entities.ProductPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;
import java.util.logging.Logger;

@Stateless
public class InventoryItemBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;
    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private ProductPackageBean productPackageBean;
    private static final Logger logger = Logger.getLogger("ejbs.InventoryItem");

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM InventoryItem p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(long productId, List<Long> productPackageIds) throws MyEntityNotFoundException {
        try {
            Product product = productBean.find(productId);
            List<ProductPackage> productPackages = productPackageBean.getProductPackages(productPackageIds);
            InventoryItem inventoryItem = new InventoryItem(product);

            for (ProductPackage productPackage : productPackages) {
                inventoryItem.addProductPackage(productPackage);
            }

            product.addInventoryItem(inventoryItem);
            Manufacturer manufacturer = manufacturerBean.find(product.getMaker().getUsername());
            inventoryItem.setMaker(manufacturer);
            product.setMaker(manufacturer);
            manufacturer.addInventoryItem(inventoryItem);

            entityManager.persist(inventoryItem);

            return inventoryItem.getId();
        } catch (Exception e) {
            logger.severe("Error creating InventoryItem: " + e.getMessage());
            throw e;
        }
    }


    // Read
    public List<InventoryItem> getAll() {
        return entityManager.createNamedQuery("getAllInventoryItems", InventoryItem.class).getResultList();
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
    public long update(long id, List<Long> productPackagesIds) throws MyEntityNotFoundException {
        InventoryItem inventoryItem = find(id);

        if (productPackagesIds != null) {
            List<ProductPackage> productPackages = productPackageBean.getProductPackages(productPackagesIds);
            inventoryItem.setProductPackages(productPackages);
        }

        entityManager.merge(inventoryItem);
        return inventoryItem.getId();
    }

    // Delete
    public void delete(long id) throws MyEntityNotFoundException {
        InventoryItem inventoryItem = find(id);
        inventoryItem.getProduct().removeInventoryItem(inventoryItem);
        entityManager.remove(find(id));
    }
}
