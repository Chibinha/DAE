package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

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

    public ProductPackage() {
    }

    public ProductPackage(long id, int packageType, String material, PhysicalProduct product) {
        super(id, packageType, material);
        this.product = product;
    }

    public PhysicalProduct getProduct() {
        return product;
    }

    public void setProduct(PhysicalProduct product) {
        this.product = product;
    }
}
