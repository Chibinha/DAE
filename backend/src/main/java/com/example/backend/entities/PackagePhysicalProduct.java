package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "packages_physical_product")
public class PackagePhysicalProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package physicalProductPackage;

    @ManyToOne
    @JoinColumn(name = "physical_product_id")
    private PhysicalProduct physicalProduct;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "associationDate")
    @NotNull
    private Timestamp associationDate;

    public PackagePhysicalProduct(Package physicalProductPackage, PhysicalProduct physicalProduct, Timestamp associationDate) {
        this.physicalProductPackage = physicalProductPackage;
        this.physicalProduct = physicalProduct;
        this.associationDate = associationDate;
    }

    public PackagePhysicalProduct() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Package getPhysicalProductPackage() {
        return physicalProductPackage;
    }

    public void setPhysicalProductPackage(Package physicalProductPackage) {
        this.physicalProductPackage = physicalProductPackage;
    }

    public PhysicalProduct getPhysicalProduct() {
        return physicalProduct;
    }

    public void setPhysicalProduct(PhysicalProduct physicalProduct) {
        this.physicalProduct = physicalProduct;
    }

    public Timestamp getAssociationDate() {
        return associationDate;
    }

    public void setAssociationDate(Timestamp associationDate) {
        this.associationDate = associationDate;
    }
}
