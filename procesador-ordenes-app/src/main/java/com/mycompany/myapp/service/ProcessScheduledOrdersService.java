package com.mycompany.myapp.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessScheduledOrdersService {

    private final Logger log = LoggerFactory.getLogger(ProcessScheduledOrdersService.class);
    private GetScheduledOrdersService getScheduledOrdersService;
    private ProcessOrdersService processOrdersService;
    private UpdateScheduledOrdersService updateScheduledOrdersService;

    public ProcessScheduledOrdersService(
        GetScheduledOrdersService getScheduledOrdersService,
        ProcessOrdersService processOrdersService,
        UpdateScheduledOrdersService updateScheduledOrdersService
        ) {
        this.getScheduledOrdersService = getScheduledOrdersService;
        this.processOrdersService = processOrdersService;
        this.updateScheduledOrdersService = updateScheduledOrdersService;
    }

    public JSONArray processScheduledOrders() {
        JSONArray scheduledOrdersArray = getScheduledOrdersService.getScheduledOrders();
        System.out.println(scheduledOrdersArray + " ***");
        JSONArray ordersToProcess = new JSONArray();

        // Array de IDs para actualizar las ordenes DB
        List<Long> ids = new ArrayList<>();
        
        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();

        for (Object orden : scheduledOrdersArray) {
            JSONObject orderObject = (JSONObject) orden;
            String modo = (String) orderObject.get("modo");

            if ( (modo.equals("PRINCIPIODIA") || modo.equals("AHORA")) ) {
                ordersToProcess.add(orderObject);
                ids.add((Long) orderObject.get("id"));
            }

            if ( modo.equals("FINDIA")) {
                ordersToProcess.add(orderObject);
                ids.add((Long) orderObject.get("id"));

            }
        }
        
        updateScheduledOrdersService.updateScheduledOrders(ids);

        JSONArray processedOrders = processOrdersService.processOrders(ordersToProcess);

        return processedOrders;

    }

}
