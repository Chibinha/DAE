package com.example.backend.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllPackages",
                query = "SELECT p FROM Package p ORDER BY p.id" // JPQL
        )
})
@Table(
        name = "packages",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
)
public abstract class Package implements Serializable {
    @Id
    protected long id;
    protected int packageType;
    protected String material;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "package")
    protected List<Sensor> valores;

    public Package() {
        this.valores = new LinkedList<Sensor>();
    }

    public Package(long id, int tipoEmbalagem, String material) {
        this.id = id;
        this.packageType = tipoEmbalagem;
        this.material = material;
        this.valores = new LinkedList<Sensor>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Sensor> getValores() {
        return valores;
    }

    public void setValores(List<Sensor> valores) {
        this.valores = valores;
    }

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int tipoEmbalagem) {
        this.packageType = tipoEmbalagem;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
