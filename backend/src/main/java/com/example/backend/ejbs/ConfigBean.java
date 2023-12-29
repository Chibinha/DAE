package com.example.backend.ejbs;

import jakarta.ejb.EJB;

import java.util.Date;

public class ConfigBean {

    @EJB
    EcomendaBean ecomendaBean;

    public void populateDB(){
        System.out.println("Hello!!");
        Date date = new Date();

        //ecomendaBean.create(1, date.toString(2024.01.11), "Alfredo", "Joaquim", "Delivered");
    }
}
