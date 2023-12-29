package com.example.backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class Order {
    @Id
    private int idEncomenda;
    @Temporal(TemporalType.DATE)
    private Date dataEncomenda;
    @NotNull
    private String consumidorFinal;
    private String operadorLogistica;
    private String estadoEncomenda;

    public Order() {

    }

    public Order(int idEncomenda, Date dataEncomenda, String consumidorFinal, String operadorLogistica, String estadoEncomenda) {
        this.idEncomenda = idEncomenda;
        this.dataEncomenda = dataEncomenda;
        this.consumidorFinal = consumidorFinal;
        this.operadorLogistica = operadorLogistica;
        this.estadoEncomenda = estadoEncomenda;
    }

    public int getIdEncomenda() {
        return idEncomenda;
    }

    public void setIdEncomenda(int idEncomenda) {
        this.idEncomenda = idEncomenda;
    }

    public Date getDataEncomenda() {
        return dataEncomenda;
    }

    public void setDataEncomenda(Date dataEncomenda) {
        this.dataEncomenda = dataEncomenda;
    }

    public String getConsumidorFinal() {
        return consumidorFinal;
    }

    public void setConsumidorFinal(String consumidorFinal) {
        this.consumidorFinal = consumidorFinal;
    }

    public String getOperadorLogistica() {
        return operadorLogistica;
    }

    public void setOperadorLogistica(String operadorLogistica) {
        this.operadorLogistica = operadorLogistica;
    }

    public String getEstadoEncomenda() {
        return estadoEncomenda;
    }

    public void setEstadoEncomenda(String estadoEncomenda) {
        this.estadoEncomenda = estadoEncomenda;
    }
}
