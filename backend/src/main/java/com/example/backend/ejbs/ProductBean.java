package com.example.backend.ejbs;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
@NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product p ORDER BY p.name")
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(Long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(String name, String description, double weight, String ingredients) {
        Product product = new Product(name, description, weight, ingredients);
        entityManager.persist(product);

        return product.getId();
    }

    // Read
    public List<Product> getAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    // Find
    public Product find(Long id) throws MyEntityNotFoundException {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id " + id + " not found");
        }
        return product;
    }

    // Update
    public void update(Long id, ProductDTO productDTO) throws MyEntityNotFoundException {
        Product product = find(id);

        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }
        if (productDTO.getWeight() != 0) {
            product.setWeight(productDTO.getWeight());
        }
        if (productDTO.getIngredients() != null) {
            product.setIngredients(productDTO.getIngredients());
        }
        entityManager.merge(product);
    }

    // Delete
    public void delete(Long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }
}
