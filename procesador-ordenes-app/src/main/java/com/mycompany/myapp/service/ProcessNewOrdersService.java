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

    @Scheduled(cron = "0 * * * * 1-5")
    public JSONArray processNewOrders() {
        log.info("Procesador de nuevas Ordenes en ejecucion");

        // Obtenemos las ordenes pendientes. La funcion devuelve un objeto del tipo OrdersValidationResource, que tiene dos atributos.
        log.info("Obteniendo ordenes pendientes...");
        JSONArray ordersArray = getPendingOrdersService.getPendingOrders();
        log.info("Cantidad de ordenes pendientes para procesar: " + ordersArray.size());
        log.info("Ordenes pendientes para procesar: " + ordersArray);

        OrdersValidationResultService allOrders = validatePendingOrdersService.validatePendingOrders(ordersArray);
        JSONArray validatedOrders = allOrders.getValidatedOrders();
        log.info("Cantidad de ordenes validadas: " + validatedOrders.size());
        log.info("Ordenes validadas: " + validatedOrders);

        JSONArray incompleteOrders = allOrders.getIncompleteOrders();
        log.info("Cantidad de ordenes incompletas: " + incompleteOrders.size());

        // Creamos los records en la entidad Ordenes. 
        createOrdenService.saveAllFromJsonArray(incompleteOrders);

        // Procesamos las ordenes validadas con modo AHORA
        log.info("Validando ordenes...");
        JSONArray processedOrders = processOrdersNowService.processOrdersNow(validatedOrders);
        if (! processedOrders.isEmpty()) {
            log.info("Ordenes procesadas en modo AHORA: " + processedOrders.size());
        } else {
            log.info("Ninguna orden fue procesada");
        }

        // Guardamos en la base de datos las ordenes procesadas
        createOrdenService.saveAllFromJsonArray(processedOrders);

        // Devolvemos un array con las ordenes procesadas y reportadas
        return processedOrders;
    }
}
