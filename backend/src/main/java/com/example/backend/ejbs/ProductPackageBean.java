package com.example.backend.ejbs;

import com.example.backend.dtos.ProductPackageDTO;
import com.example.backend.entities.ProductPackage;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

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

    public long create(int packageType, String material) throws MyEntityNotFoundException{
        ProductPackage productPackage = new ProductPackage(packageType, material);
        entityManager.persist(productPackage);

        find(productPackage.getId());

        return productPackage.getId();
    }

    public ProductPackage find(long id) throws MyEntityNotFoundException {
        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
        if (productPackage == null) {
            throw new MyEntityNotFoundException("Package with id " + id + " not found");
        }
        return productPackage;
    }

    public List<ProductPackage> getAll() {
        // remember, maps to: “SELECT s FROM Student s ORDER BY s.name”
        return entityManager.createNamedQuery("getAllProductPackages", ProductPackage.class).getResultList();
    }

    public long update(long id, ProductPackageDTO productPackageDTO) throws MyEntityNotFoundException {
        ProductPackage productPackage = find(id);

        if (productPackageDTO.getPackageType() != 0) {
            productPackage.setPackageType(productPackageDTO.getPackageType());
        }
        if (productPackageDTO.getMaterial() != null) {
            productPackage.setMaterial(productPackageDTO.getMaterial());
        }

        entityManager.merge(productPackage);
        return productPackage.getId();
    }

    public void delete(long id) throws MyEntityNotFoundException{
        entityManager.remove(find(id));
    }

//    public void associateProductPackageToOrder(long id, long productId) {
//        InventoryItem product = entityManager.find(InventoryItem.class, productId);
//        if(product == null)
//            return;
//        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
//        if(productPackage == null)
//            return;
//        else {
//            //product.addProductPackage(productPackage);
//        }
//    }
//
//    public void dissociateProductPackageFromOrder(long id, long productId) {
//        InventoryItem product = entityManager.find(InventoryItem.class, productId);
//        if(product == null)
//            return;
//        ProductPackage productPackage = entityManager.find(ProductPackage.class, id);
//        if(productPackage == null)
//            return;
//        else {
//            //product.removeProductPackage(productPackage);
//        }
//    }
//
}
