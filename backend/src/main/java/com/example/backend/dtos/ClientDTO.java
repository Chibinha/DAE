package com.example.backend.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientDTO implements Serializable {
    private String username;
    private String password;
    private String name;
    private String email;

    private List<OrderDTO> orderDTOS;


    public ClientDTO(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.orderDTOS = new ArrayList<OrderDTO>();
    }

    public ClientDTO() {this.orderDTOS = new ArrayList<OrderDTO>();}

    public List<OrderDTO> getOrdersDTOS() {return orderDTOS;}

    public void setOrderDTOS(List<OrderDTO> orderDTOS) {this.orderDTOS = orderDTOS;}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
