package com.example.backend.ejbs;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.Maker;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(long id) {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.id = :id");
        query.setParameter("id", id);
        return ((long) query.getSingleResult()) > 0L;
    }

    // CRUD
    // Create
    public long create(String name,double price, String description, double weight, String ingredients, String makerName) throws MyEntityNotFoundException {
        Maker maker = entityManager.find(Maker.class, makerName);

        Product product = new Product(name, price, description, weight, ingredients, maker);
        entityManager.persist(product);

        find(product.getId());

        return product.getId();
    }

    // Read
    public List<Product> getAll() {
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    // Find
    public Product find(long id) throws MyEntityNotFoundException {
        Product product = entityManager.find(Product.class, id);
        if (product == null) {
            throw new MyEntityNotFoundException("Product with id " + id + " not found");
        }
        return product;
    }

    // Update
    public void update(long id, ProductDTO productDTO) throws MyEntityNotFoundException {
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
    public void delete(long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }
}
