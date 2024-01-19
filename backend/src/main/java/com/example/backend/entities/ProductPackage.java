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
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private PhysicalProduct product;

    //physical product
    @ManyToMany
    @JoinTable(
            name = "product_package_physical_product",
            joinColumns = @JoinColumn(name = "product_package_id"),
            inverseJoinColumns = @JoinColumn(name = "physical_product_id")
    )
    private List<PhysicalProduct> physicalProducts;

    public ProductPackage() {
        this.physicalProducts = new ArrayList<>();
    }

    public ProductPackage(long id, int packageType, String material, PhysicalProduct product) {
        super(id, packageType, material);
        this.product = product;
        this.physicalProducts = new ArrayList<>();
    }

    public PhysicalProduct getProduct() {
        return product;
    }

    public void setProduct(PhysicalProduct product) {
        this.product = product;
    }

    public List<PhysicalProduct> getPhysicalProducts() {
        return physicalProducts;
    }

    public void setPhysicalProducts(List<PhysicalProduct> physicalProducts) {
        this.physicalProducts = physicalProducts;
    }
}
