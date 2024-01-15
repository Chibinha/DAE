package com.example.backend.ejbs;

import com.example.backend.entities.*;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityExistsException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;

import java.util.List;

@Stateless
public class ProductPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public boolean exists(long id) {
        Query query = entityManager.createQuery(
                "SELECT COUNT(p.id) FROM ProductPackage p WHERE p.id = :id", Long.class);
        query.setParameter("id", id);
        return (Long) query.getSingleResult() > 0L;
    }

    public ProductPackage create(long id, int packageType, String material, long product_id) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(id)) {
            throw new MyEntityExistsException(
                    "Package with id '" + id + "' already exists"
            );
        }
        PhysicalProduct product = entityManager.find(PhysicalProduct.class, product_id);
        if (product == null) {
            throw new MyEntityNotFoundException(
                    "Product with id '" + product_id + "' not found"
            );
        }

        ProductPackage productPackage = null;

        try {
            productPackage = new ProductPackage(id, packageType, material, product);
            this.entityManager.persist(productPackage);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        return productPackage;
    }

    public ProductPackage find(long id) {
        return entityManager.find(ProductPackage.class, id);
    }

    public List<ProductPackage> getAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllProductPackages", ProductPackage.class).getResultList();
    }

    public void associateProductPackageToOrder(long id, long productId) {
        PhysicalProduct product = entityManager.find(PhysicalProduct.class, productId);
        if(product == null)
            return;
        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
        if(productPackage == null)
            return;
        else {
            //product.addProductPackage(productPackage);
        }
    }

    public void dissociateProductPackageFromOrder(long id, long productId) {
        PhysicalProduct product = entityManager.find(PhysicalProduct.class, productId);
        if(product == null)
            return;
        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
        if(productPackage == null)
            return;
        else {
            //product.removeProductPackage(productPackage);
        }
    }

    public void removeProductPackage(long id){
        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
        if (productPackage != null) {
            entityManager.remove(productPackage);
        }
    }


    public ProductPackage update(long id, int packageType, String material) throws MyEntityNotFoundException {
        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
        if(productPackage == null)
            throw new MyEntityNotFoundException("Product Package with id '" + id + "' not found");

        entityManager.lock(productPackage, LockModeType.OPTIMISTIC);
        productPackage.setPackageType(packageType);
        productPackage.setMaterial(material);

        return productPackage;
    }
}
