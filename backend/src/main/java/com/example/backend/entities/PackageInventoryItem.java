package com.example.backend.entities;

import com.example.backend.entities.InventoryItem;
import com.example.backend.entities.Package;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "packages_inventoryitems")
public class PackageInventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Package physicalProductPackage;

    @ManyToOne
    @JoinColumn(name = "physical_product_id")
    private InventoryItem inventoryItem;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "associationDate")
    @NotNull
    private Timestamp associationDate;

    public PackageInventoryItem(Package physicalProductPackage, InventoryItem inventoryItem, Timestamp associationDate) {
        this.physicalProductPackage = physicalProductPackage;
        this.inventoryItem = inventoryItem;
        this.associationDate = associationDate;
    }

    public PackageInventoryItem() {
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

    public InventoryItem getPhysicalProduct() {
        return inventoryItem;
    }

    public void setPhysicalProduct(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Timestamp getAssociationDate() {
        return associationDate;
    }

    public void setAssociationDate(Timestamp associationDate) {
        this.associationDate = associationDate;
    }
}
