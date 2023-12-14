package com.mycompany.myapp.web.rest;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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


    public HTTPResource(
        GetPendingOrdersService getPendingOrdersService, 
        ValidatePendingOrdersService validatePendingOrdersService, 
        OrdersValidationResultService ordersValidationResultService,
        ProcessOrdersService processOrdersService
        ) {
        this.getPendingOrdersService = getPendingOrdersService;
        this.validatePendingOrdersService = validatePendingOrdersService;
        this.ordersValidationResultService = ordersValidationResultService;
        this.processOrdersService = processOrdersService;
     }


    /**
     * GET getOrder
     */
    @GetMapping("/get-order")
    public JSONArray getOrder() {
        JSONArray ordersArray = getPendingOrdersService.getPendingOrders();
        OrdersValidationResultService allOrders = validatePendingOrdersService.validatePendingOrders(ordersArray);
        System.out.println(allOrders.getValidatedOrders());
        JSONArray validatedOrders = allOrders.getValidatedOrders();
        JSONArray ordersToProcess = new JSONArray();
        for (Object orden : validatedOrders) {
            JSONObject ordenObject = (JSONObject) orden;
            String modo = (String) ordenObject.get("modo");
            if (modo.equals("AHORA")) {
                ordersToProcess.add(orden);
            }
        }
        processOrdersService.processOrders(ordersToProcess);

        return ordersToProcess;
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
