package com.example.backend.ejbs;

import com.example.backend.entities.Order;
import com.example.backend.entities.PhysicalProduct;
import com.example.backend.entities.ProductPackage;
import com.example.backend.entities.TransportationPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateless
public class ProductPackageBean {
    @PersistenceContext
    private EntityManager entityManager;

    public void create(long id, int packageType, String material, long product_id) {
        ProductPackage productPackage = new ProductPackage(id, packageType, material, product_id);
        this.entityManager.persist(productPackage);
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
            product.addProductPackage(productPackage);
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
            product.removeProductPackage(productPackage);
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
