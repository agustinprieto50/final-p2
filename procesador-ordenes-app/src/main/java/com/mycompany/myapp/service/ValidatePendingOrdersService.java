package com.mycompany.myapp.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ValidatePendingOrdersService {

    private final Logger log = LoggerFactory.getLogger(ValidatePendingOrdersService.class);

    private final GetClientService getClientService;
    private final GetStockService getStockService;
    private final OrdersValidationResultService ordersValidationResultService;


    @Value("${spring.variables.api_token}")
    private String token;

    public ValidatePendingOrdersService(
            GetClientService getClientService,
            GetStockService getStockService,
            OrdersValidationResultService ordersValidationResultService
    ) {
        this.getClientService = getClientService;
        this.getStockService = getStockService;
        this.ordersValidationResultService = ordersValidationResultService;
    }

    public OrdersValidationResultService validatePendingOrders(JSONArray ordersArray) {
        JSONArray validatedOrders = new JSONArray();
        JSONArray incompleteOrders = new JSONArray();

        for (Object orden : ordersArray) {
            JSONObject ordenObject = (JSONObject) orden;

            Long clienteId = (Long) ordenObject.get("cliente");
            String accionCodigo = (String) ordenObject.get("accion");
            Long accionId = (Long) ordenObject.get("accionId");

            boolean clientExists = getClientService.getClient(clienteId);
            boolean stockExists = getStockService.getStock(accionCodigo, accionId);

            if (clientExists && stockExists) {
                ordenObject.put("status", "scheduled");
                validatedOrders.add(ordenObject);
            } else {
                ordenObject.put("status", "hasErrors");
                incompleteOrders.add(ordenObject);
            }
        }
        return new OrdersValidationResultService(validatedOrders, incompleteOrders);
    }
}
