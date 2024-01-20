package com.example.backend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

@Entity
@Table(name = "productpackages_inventoryitems")
public class ProductPackage_InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "productpackage_id")
    private ProductPackage myPackage;

    @ManyToOne
    @JoinColumn(name = "inventoryitem_id")
    private InventoryItem inventoryItem;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "association_date")
    @NotNull
    private Timestamp associationDate;

    public ProductPackage_InventoryItem(ProductPackage myPackage, InventoryItem inventoryItem, Timestamp associationDate) {
        this.myPackage = myPackage;
        this.inventoryItem = inventoryItem;
        this.associationDate = associationDate;
    }

    public ProductPackage_InventoryItem() {
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
