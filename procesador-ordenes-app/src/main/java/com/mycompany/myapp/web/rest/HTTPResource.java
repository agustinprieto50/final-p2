package com.mycompany.myapp.web.rest;


import java.time.LocalTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.myapp.service.CreateOrdenService;
import com.mycompany.myapp.service.GetPendingOrdersService;
import com.mycompany.myapp.service.ValidatePendingOrdersService;


import com.mycompany.myapp.service.OrdersValidationResultService;
import com.mycompany.myapp.service.ProcessOrdersService;

/**
 * HTTPResource controller
 */
@RestController
@RequestMapping("/api/http")
public class HTTPResource {

    private final Logger log = LoggerFactory.getLogger(HTTPResource.class);
    private GetPendingOrdersService getPendingOrdersService;
    private ValidatePendingOrdersService validatePendingOrdersService;
    private OrdersValidationResultService ordersValidationResultService;
    private ProcessOrdersService processOrdersService;
    private CreateOrdenService createOrdenService;


    public HTTPResource(
        GetPendingOrdersService getPendingOrdersService, 
        ValidatePendingOrdersService validatePendingOrdersService, 
        OrdersValidationResultService ordersValidationResultService,
        ProcessOrdersService processOrdersService,
        CreateOrdenService createOrdenService
        ) {
        this.getPendingOrdersService = getPendingOrdersService;
        this.validatePendingOrdersService = validatePendingOrdersService;
        this.ordersValidationResultService = ordersValidationResultService;
        this.processOrdersService = processOrdersService;
        this.createOrdenService = createOrdenService;
     }


    /**
     * GET getOrder
     */
    @GetMapping("/app")
    public JSONArray app() {
        // Obtenemos las ordenes pendientes. La funcion devuelve un objeto del tipo OrdersValidationResource, que tiene dos atributos.
        JSONArray ordersArray = getPendingOrdersService.getPendingOrders();
        OrdersValidationResultService allOrders = validatePendingOrdersService.validatePendingOrders(ordersArray);
        JSONArray validatedOrders = allOrders.getValidatedOrders();
        JSONArray incompleteOrders = allOrders.getIncompleteOrders();

        JSONArray ordersToSaveNow = new JSONArray();

        // Creamos los records en la entidad Ordenes. 
        createOrdenService.saveAllFromJsonArray(incompleteOrders);

        // Procesa aquellas ordenes con modo "AHORA"
        // Si el modo es AHORA y la hora es entre las 9 y las 18, entonces procesa la orden.
        // Si no la agrega a la lista de ordenes para ser guardada en la base de datos con status: scheduled.
        JSONArray ordersToProcess = new JSONArray();
        for (Object orden : validatedOrders) {
            JSONObject ordenObject = (JSONObject) orden;
            String modo = (String) ordenObject.get("modo");
            if (modo.equals("AHORA")) {
                LocalTime currentTime = LocalTime.now();
                int currentHour = currentTime.getHour();
                boolean isBetween9And6 = currentHour >= 9 && currentHour < 18;
                if (isBetween9And6) {
                    ordersToProcess.add(orden);
                } else {
                    ordersToSaveNow.add(orden);
                }

            } else {
                ordersToSaveNow.add(orden);
            }
        }
        createOrdenService.saveAllFromJsonArray(ordersToSaveNow);
        JSONArray processedOrders = processOrdersService.processOrders(ordersToProcess);
        createOrdenService.saveAllFromJsonArray(processedOrders);


        return processedOrders;
    }

    /**
     * GET getAccion
     */
    @GetMapping("/get-accion")
    public String getAccion() {
        return "getAccion";
    }

    /**
     * GET getCliente
     */
    @GetMapping("/get-cliente")
    @ResponseBody
    public String getCliente() {
        return "getCLiente";
    }
}
