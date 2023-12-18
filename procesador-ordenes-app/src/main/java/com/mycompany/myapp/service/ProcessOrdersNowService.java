package com.mycompany.myapp.service;

import java.time.LocalTime;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessOrdersNowService {

    private final Logger log = LoggerFactory.getLogger(ProcessOrdersNowService.class);
    private CreateOrdenService createOrdenService;
    private ProcessOrdersService processOrdersService;

    public ProcessOrdersNowService(
        CreateOrdenService createOrdenService,
        ProcessOrdersService processOrdersService
        ) {
        this.createOrdenService = createOrdenService;
        this.processOrdersService = processOrdersService;
    }

    public JSONArray processOrdersNow(JSONArray validatedOrders) {

        JSONArray ordersToSaveNow = new JSONArray();

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
        // Llama al metodo que procesa y reporta las ordenes
        JSONArray processedOrders = processOrdersService.processOrders(ordersToProcess);


        return processedOrders;
    }
}
