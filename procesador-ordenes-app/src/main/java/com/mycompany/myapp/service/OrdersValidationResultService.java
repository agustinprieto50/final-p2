package com.mycompany.myapp.service;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrdersValidationResultService {

    private final Logger log = LoggerFactory.getLogger(OrdersValidationResultService.class);


    private final JSONArray validatedOrders;
    private final JSONArray incompleteOrders;

    public OrdersValidationResultService(JSONArray validatedOrders, JSONArray incompleteOrders) {
        this.validatedOrders = validatedOrders;
        this.incompleteOrders = incompleteOrders;
    }

    public JSONArray getValidatedOrders() {
        return validatedOrders;
    }

    public JSONArray getIncompleteOrders() {
        return incompleteOrders;
    }
}
