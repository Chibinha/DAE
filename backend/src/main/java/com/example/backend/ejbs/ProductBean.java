package com.example.backend.ejbs;

import com.example.backend.dtos.ProductDTO;
import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Manufacturer;
import com.example.backend.entities.Product;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.hibernate.Hibernate;

import java.sql.Timestamp;
import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(long id) throws MyEntityNotFoundException  {
        Query query = entityManager.createQuery("SELECT COUNT(p) FROM Product p WHERE p.id = :id");
        query.setParameter("id", id);
        if ((long) query.getSingleResult() > 0L) {
            return true;
        } else {
            throw new MyEntityNotFoundException("Product with id " + id + " not found");
        }
    }

    // CRUD
    // Create
    public long create(String name,double price, String description, double weight, String ingredients, String makerName) throws MyEntityNotFoundException {
        Manufacturer manufacturer = entityManager.find(Manufacturer.class, makerName);

        Product product = new Product(name, price, description, weight, ingredients, manufacturer);
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
    public long update(long id, ProductDTO productDTO) throws MyEntityNotFoundException {
        Product product = find(id);

        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }
        if (productDTO.getPrice() != 0) {
            product.setPrice(productDTO.getPrice());
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
        //set updated time
        product.setUpdateTimestamp(new Timestamp(System.currentTimeMillis()));

        entityManager.merge(product);
        return product.getId();
    }

    // Delete
    public void delete(long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }

    //get List<InventoryItem> from Product
    public List<InventoryItem> getListPhysicalProducts(long productId) {
        Product product = entityManager.find(Product.class, productId);
        if (product != null) {
            Hibernate.initialize(product.getPhysicalProducts());
            return product.getPhysicalProducts();
        }
        return null;
    }
}
