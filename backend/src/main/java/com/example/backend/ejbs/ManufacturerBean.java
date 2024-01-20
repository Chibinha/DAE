package com.example.backend.ejbs;

import com.example.backend.dtos.InventoryItemDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.dtos.ProductPackageDTO;
import com.example.backend.entities.*;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Product;
import com.example.backend.entities.Manufacturer;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Map;

@Stateless
public class ManufacturerBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;
    @EJB
    private InventoryItemBean inventoryItemBean;
    @EJB
    private ProductPackageBean productPackageBean;
    private Hasher hasher;

    //#region CRUD
    // Create
    public void create(String username, String password, String name, String email) {
        hasher = new Hasher();
        Manufacturer manufacturer = new Manufacturer(username, hasher.hash(password), name, email);
        entityManager.persist(manufacturer);
    }

    // Read
    public List<Manufacturer> getAll() {
        return entityManager.createNamedQuery("getAllMakers", Manufacturer.class).getResultList();
    }

    // Find
    public Manufacturer find(String username) throws MyEntityNotFoundException {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, username);
        if (manufacturer == null) {
            throw new MyEntityNotFoundException("Manufacturer with username " + username + " not found");
        }
        return manufacturer;
    }

    // Update
    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException {
        Manufacturer manufacturer = find(username);

        if (password != null) {
            hasher = new Hasher();
            manufacturer.setPassword(hasher.hash(password));
        }
        if (name != null) {
            manufacturer.setName(name);
        }
        if (email != null) {
            manufacturer.setEmail(email);
        }

        entityManager.merge(manufacturer);
    }

    // Delete
    public void delete(String username) throws MyEntityNotFoundException {
        Manufacturer manufacturer = find(username);
        entityManager.remove(manufacturer);
    }
    //#endregion

    //#region Products

    // Create Own Product
    public long createProduct(String name, double price, String description, double weight, String ingredients, String makerUsername ) throws MyEntityNotFoundException {
        long id = productBean.create(name, price, description, weight, ingredients, makerUsername);
        productBean.exists(id);
        return id;
    }

    // Update Own Product
    public long updateProduct(long productId, ProductDTO productDTO) throws MyEntityNotFoundException {
        return productBean.update(productId, productDTO);
    }

    // Get Own Products
    public List<Product> getProducts(String username) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerProducts", Product.class)
                .setParameter("username", maker.getUsername())
                .getResultList();
    }
    //#endregion

    //#region InventoryItems
    //createInventoryItemList
    public void createInventoryItemList(long productId, int amount, List<Long> packageIds) throws MyEntityNotFoundException {
        for (int i = 0; i < amount; i++) {
            inventoryItemBean.create(productId, packageIds);
            inventoryItemBean.exists(productId);
        }
    }

    // Update InventoryItem
    public long updateInventoryItem(long InventoryItemId, List<Long> packageIds) throws MyEntityNotFoundException {
        return inventoryItemBean.update(InventoryItemId, packageIds);
    }

    //get Inventory Items for specific product
    public List<InventoryItem> getInventoryItemsForProduct(String username, long productId) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerInventoryItemsForProduct", InventoryItem.class)
                .setParameter("username", maker.getUsername())
                .setParameter("productId", productId)
                .getResultList();
    }

    public List<InventoryItem> getAllInventoryItems(String username) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerInventoryItems", InventoryItem.class)
                .setParameter("username", maker.getUsername())
                .getResultList();
    }
    //#endregion

    //#region Alerts
    public List<Alert> getAlerts(String username) throws MyEntityNotFoundException {
        var user = find(username);
        return entityManager.createNamedQuery("getUserAlerts", Alert.class)
                .setParameter("user", user)
                .getResultList();
    }

    public void deleteInventoryItem(long InventoryItemId) throws MyEntityNotFoundException {
        inventoryItemBean.delete(InventoryItemId);
    }
    //#endregion

    //#region Packages
    // Get Packages
    public List<ProductPackage> getPackages(String username) throws MyEntityNotFoundException {
        find(username);
        return entityManager.createNamedQuery("getAllProductPackages", ProductPackage.class).getResultList();
    }

    //#endregion
}
