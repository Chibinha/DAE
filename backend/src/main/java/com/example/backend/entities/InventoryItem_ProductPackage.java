package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "productpackages_inventoryitems")
public class InventoryItem_ProductPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "inventory_item_id")
    private InventoryItem inventoryItem;

    @ManyToOne
    @JoinColumn(name = "product_package_id")
    private ProductPackage myPackage;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "association_date")
    @NotNull
    private Timestamp associationDate;

    public InventoryItem_ProductPackage(ProductPackage myPackage, InventoryItem inventoryItem, Timestamp associationDate) {
        this.myPackage = myPackage;
        this.inventoryItem = inventoryItem;
        this.associationDate = associationDate;
    }

    public InventoryItem_ProductPackage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Package getMyPackage() {
        return myPackage;
    }

    public void setMyPackage(ProductPackage InventoryItemPackage) {
        this.myPackage = InventoryItemPackage;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public Timestamp getAssociationDate() {
        return associationDate;
    }

    public void setAssociationDate(Timestamp associationDate) {
        this.associationDate = associationDate;
    }
}
