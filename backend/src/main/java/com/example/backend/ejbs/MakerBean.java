package com.example.backend.ejbs;

import com.example.backend.dtos.PhysicalProductDTO;
import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.*;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import com.example.backend.security.Hasher;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import jakarta.validation.ConstraintViolationException;

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
//
//    public boolean exists(String username) throws MyEntityNotFoundException {
//        Query query = entityManager.createQuery("SELECT COUNT(m) FROM Maker m WHERE m.username = :username");
//        query.setParameter("username", username);
//        if ((long) query.getSingleResult() > 0L) {
//            return true;
//        } else {
//            throw new MyEntityNotFoundException("Maker with username " + username + " not found");
//        }
//    }
//
//    // CRUD
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

    // Own Products
    public List<Product> getProducts(String username) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerProducts", Product.class)
                .setParameter("username", maker.getUsername())
                .getResultList();
    }

    public List<PhysicalProduct> getPhysicalProducts(String username) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerPhysicalProducts", PhysicalProduct.class)
                .setParameter("username", maker.getUsername())
                .getResultList();
    }

    //get physical products for specific product
    public List<PhysicalProduct> getPhysicalProductsForProduct(String username, long productId) throws MyEntityNotFoundException {
        var maker = find(username);
        return entityManager.createNamedQuery("getMakerPhysicalProductsForProduct", PhysicalProduct.class)
                .setParameter("username", maker.getUsername())
                .setParameter("productId", productId)
                .getResultList();
    }

    public long createProduct(String name, double price, String description, double weight, String ingredients, String makerUsername ) throws MyEntityNotFoundException {
        long id = productBean.create(name, price, description, weight, ingredients, makerUsername);
        productBean.exists(id);
        return id;
    }

    public long createPhysicalProduct(String serialNumber, long productId) throws MyEntityNotFoundException {
        long id = physicalProductBean.create(serialNumber, productId);
        physicalProductBean.exists(id);
        return id;
    }

    //createPhysicalProductList
    public void createPhysicalProductList(List<PhysicalProductDTO> physicalProductList) throws MyEntityNotFoundException {
        for (PhysicalProductDTO physicalProductDTO : physicalProductList) {
            createPhysicalProduct(physicalProductDTO.getSerialNumber(), physicalProductDTO.getProductId());
        }
    }

    public List<Alert> getAlerts(String username) throws MyEntityNotFoundException {
        var user = find(username);
        return entityManager.createNamedQuery("getUserAlerts", Alert.class)
                .setParameter("user", user)
                .getResultList();
    }

    public long updateProduct(long productId, ProductDTO productDTO) throws MyEntityNotFoundException {
        return productBean.update(productId, productDTO);
    }
}