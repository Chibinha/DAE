//package com.example.backend.entities;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//
//import java.io.Serializable;
//
//@Entity
//@Table(name = "alert")
//@NamedQueries({
//        @NamedQuery(
//                name = "getAllAlerts",
//                query = "SELECT a FROM Alert a ORDER BY a.id"
//        )
//})
//public class Alert implements Serializable {
//
//    @Id
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "username")
//    @NotNull
//    public Client client;
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//}
