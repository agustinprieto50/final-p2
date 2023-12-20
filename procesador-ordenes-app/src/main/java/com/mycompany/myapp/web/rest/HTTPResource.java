package com.mycompany.myapp.web.rest;



import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private ProcessNewOrdersService processNewOrdersService;

    @Autowired
    private ProcessScheduledOrdersService processScheduledOrdersService;


    /**
     * GET getOrder
     */
    @GetMapping("/app")
    public JSONArray app() {
        JSONArray processedOrders = processNewOrdersService.processNewOrders();
        return processedOrders;
    }

    /**
     * GET processScheduledOrders
     */
    @GetMapping("/process-scheduled-orders")
    public JSONArray processScheduledOrders() {
        JSONArray processedOrders = processScheduledOrdersService.processScheduledOrders();
        return processedOrders;
    }
    
}
