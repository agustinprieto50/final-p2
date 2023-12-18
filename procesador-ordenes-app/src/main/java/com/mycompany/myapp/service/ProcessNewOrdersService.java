package com.mycompany.myapp.service;

import java.time.LocalTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessNewOrdersService {

    private final Logger log = LoggerFactory.getLogger(ProcessNewOrdersService.class);
    private GetPendingOrdersService getPendingOrdersService;
    private ValidatePendingOrdersService validatePendingOrdersService;
    private ProcessOrdersService processOrdersService;
    private CreateOrdenService createOrdenService;
    private ProcessOrdersNowService processOrdersNowService;

    public ProcessNewOrdersService(
        GetPendingOrdersService getPendingOrdersService, 
        ValidatePendingOrdersService validatePendingOrdersService, 
        ProcessOrdersService processOrdersService,
        CreateOrdenService createOrdenService,
        ProcessOrdersNowService processOrdersNowService
    ) {
        this.getPendingOrdersService = getPendingOrdersService;
        this.validatePendingOrdersService = validatePendingOrdersService;
        this.processOrdersService = processOrdersService;
        this.createOrdenService = createOrdenService;
        this.processOrdersNowService = processOrdersNowService;
    }


    public JSONArray processNewOrders() {

        // Obtenemos las ordenes pendientes. La funcion devuelve un objeto del tipo OrdersValidationResource, que tiene dos atributos.
        JSONArray ordersArray = getPendingOrdersService.getPendingOrders();
        OrdersValidationResultService allOrders = validatePendingOrdersService.validatePendingOrders(ordersArray);
        JSONArray validatedOrders = allOrders.getValidatedOrders();
        JSONArray incompleteOrders = allOrders.getIncompleteOrders();

        // Creamos los records en la entidad Ordenes. 
        createOrdenService.saveAllFromJsonArray(incompleteOrders);

        // Procesamos las ordenes validadas con modo AHORA
        JSONArray processedOrders = processOrdersNowService.processOrdersNow(validatedOrders);

        // Guardamos en la base de datos las ordenes procesadas
        createOrdenService.saveAllFromJsonArray(processedOrders);

        // Devolvemos un array con las ordenes procesadas y reportadas
        return processedOrders;
    }
}
