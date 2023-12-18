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

import com.mycompany.myapp.service.ProcessNewOrdersService;
import com.mycompany.myapp.service.ProcessScheduledOrdersService;

/**
 * HTTPResource controller
 */
@RestController
@RequestMapping("/api/http")
public class HTTPResource {

    private final Logger log = LoggerFactory.getLogger(HTTPResource.class);
    private ProcessNewOrdersService processNewOrdersService;
    private ProcessScheduledOrdersService processScheduledOrdersService;

    public HTTPResource(
        ProcessNewOrdersService processNewOrdersService,
        ProcessScheduledOrdersService processScheduledOrdersService
        ) {
        this.processNewOrdersService = processNewOrdersService;
        this.processScheduledOrdersService = processScheduledOrdersService;
        }


    /**
     * GET getOrder
     */
    @GetMapping("/app")
    public JSONArray app() {

        JSONArray processedOrders = processNewOrdersService.processNewOrders();

        return processedOrders;
    }

    /**
     * GET getAccion
     */
    @GetMapping("/process-scheduled-orders")
    public void processScheduledOrders() {
        processScheduledOrdersService.processScheduledOrders();
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
