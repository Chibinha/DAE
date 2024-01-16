package com.example.backend.services;

import com.example.backend.ejbs.OrderBean;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;

import java.util.Map;

public class OrderService {

    @EJB
    private OrderBean orderBean;

    public void createOrder(String orderType, String materialType, String clientUsername, Map<Long, Integer> productQuantities) throws MyConstraintViolationException, MyEntityNotFoundException {
        orderBean.create(orderType, materialType, clientUsername, productQuantities);
    }
}
