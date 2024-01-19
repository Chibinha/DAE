package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProductPackages",
                query = "SELECT p FROM ProductPackage p ORDER BY p.id" // JPQL
        )
})
public class ProductPackage extends Package implements Serializable{
    @ManyToMany
    @JoinTable(
            name = "package_physical_product",
            joinColumns = @JoinColumn(name = "product_package_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "physical_product_id"),
            }
    )
    private List<PhysicalProduct> physicalProducts;

    public ProductPackage() {
        this.physicalProducts = new ArrayList<>();
    }

    public ProductPackage(int packageType, String material) {
        super(packageType, material);
        //this.product = product;
        this.physicalProducts = new ArrayList<>();
    }

    public PhysicalProduct getCurrentPhysicalProduct() {
        if(!physicalProducts.isEmpty())
            return physicalProducts.get(physicalProducts.size() - 1);
        return null;
    }

    public List<PhysicalProduct> getPhysicalProducts() {
        return physicalProducts;
    }

    public void setPhysicalProducts(List<PhysicalProduct> physicalProducts) {
        this.physicalProducts = physicalProducts;
    }

    public void addPhysicalProduct(PhysicalProduct physicalProduct) {
        if(physicalProduct!= null)
            this.physicalProducts.add(physicalProduct);
    }

    public void removePhysicalProduct(PhysicalProduct physicalProduct) {
        if(physicalProduct!= null)
            this.physicalProducts.remove(physicalProduct);
    }
}
