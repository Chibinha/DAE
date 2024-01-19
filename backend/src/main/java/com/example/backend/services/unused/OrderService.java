package com.example.backend.services.unused;

import com.example.backend.dtos.DTOConverter;
import com.example.backend.ejbs.OrderBean;
import com.example.backend.exceptions.MyConstraintViolationException;
import com.example.backend.exceptions.MyEntityNotFoundException;
import jakarta.ejb.EJB;

import java.util.Map;

public class OrderService {

    @EJB
    private OrderBean orderBean;
    private final DTOConverter dtoConverter = new DTOConverter();

//    public void createOrder(String orderType, String lineOperatorUsername, String clientUsername, Map<Long, Integer> productQuantities) throws MyConstraintViolationException, MyEntityNotFoundException {
//        orderBean.create(orderType, lineOperatorUsername, clientUsername, productQuantities);
//    }
}
