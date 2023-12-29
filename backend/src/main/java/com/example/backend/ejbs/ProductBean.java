package com.example.backend.ejbs;

import com.example.backend.entities.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void createProduct(String name, String description, double weight) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setWeight(weight);
        entityManager.persist(product);
    }

    public List<Product> getAllProducts() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(Long id) {
        return entityManager.find(Product.class, id);
    }
}
