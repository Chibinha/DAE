package com.example.backend.ejbs;

import com.example.backend.dtos.PhysicalProductDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.*;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;

import java.util.List;

@Stateless
public class MakerBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private ProductBean productBean;
    @EJB
    private PhysicalProductBean physicalProductBean;
    private Hasher hasher;

    //#region CRUD
    // Create
    public void create(String username, String password, String name, String email) {
        hasher = new Hasher();
        Maker maker = new Maker(username, hasher.hash(password), name, email);
        entityManager.persist(maker);
    }

    // Read
    public List<Maker> getAll() {
        return entityManager.createNamedQuery("getAllMakers", Maker.class).getResultList();
    }

    // Find
    public Maker find(String username) throws MyEntityNotFoundException {
        Maker maker = entityManager.find(Maker.class, username);
        if (maker == null) {
            throw new MyEntityNotFoundException("Maker with username " + username + " not found");
        }
        return maker;
    }

    // Update
    public void update(String username, String password, String name, String email) throws MyEntityNotFoundException {
        Maker maker = find(username);

        if (password != null) {
            hasher = new Hasher();
            maker.setPassword(hasher.hash(password));
        }
        if (name != null) {
            maker.setName(name);
        }
        if (email != null) {
            maker.setEmail(email);
        }

        entityManager.merge(maker);
    }

    // Delete
    public void delete(String username) throws MyEntityNotFoundException {
        Maker maker = find(username);
        entityManager.remove(maker);
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

    //#region PhysicalProducts
    // Create PhysicalProduct
    public long createPhysicalProduct(long productId) throws MyEntityNotFoundException {
        long id = physicalProductBean.create(productId);
        physicalProductBean.exists(id);
        return id;
    }

    //createPhysicalProductList
    public void createPhysicalProductList(long productId, int amount) throws MyEntityNotFoundException {
        for (int i = 0; i < amount; i++) {
            physicalProductBean.create(productId);
        }
    }

    // Update PhysicalProduct
    public long updatePhysicalProduct(long physicalProductId) throws MyEntityNotFoundException {
        return physicalProductBean.update(physicalProductId);
    }

    //get physical products for specific product
    public List<PhysicalProduct> getPhysicalProductsForProduct(String username, long productId) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerPhysicalProductsForProduct", PhysicalProduct.class)
                .setParameter("username", maker.getUsername())
                .setParameter("productId", productId)
                .getResultList();
    }

    public List<PhysicalProduct> getAllPhysicalProducts(String username) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerPhysicalProducts", PhysicalProduct.class)
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

    public void deletePhysicalProduct(long physicalProductId) throws MyEntityNotFoundException {
        physicalProductBean.delete(physicalProductId);
    }
    //#endregion
}