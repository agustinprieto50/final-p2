package com.mycompany.myapp.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessOrdersService {

    private final Logger log = LoggerFactory.getLogger(ProcessOrdersService.class);
    private final ValidateClientStockService validateClientStockService;
    private final ReportOrderService reportOrderService;


    public ProcessOrdersService(
        ValidateClientStockService validateClientStockService,
        ReportOrderService reportOrderService
    ) {
        this.validateClientStockService = validateClientStockService;
        this.reportOrderService = reportOrderService;
    }

    public JSONArray processOrders(JSONArray orders) {
        
        JSONArray ordersToReportArray = new JSONArray();
        

        for (Object orden : orders) {
            JSONObject ordenObject = (JSONObject) orden;
            JSONObject ordenToReport = new JSONObject();
            Long clienteId = (Long) ordenObject.get("cliente");
            Long accionId = (Long) ordenObject.get("accionId");
            String accion = (String) ordenObject.get("accion");
            String operacion = (String) ordenObject.get("operacion");
            Long cantidad = (Long) ordenObject.get("cantidad");
            Double precio = (Double) ordenObject.get("precio");
            String fechaOperacion = (String) ordenObject.get("fechaOperacion");
            String modo = (String) ordenObject.get("modo");

            boolean enoughStocks = true;

            ordenToReport.put("cliente", clienteId);
            ordenToReport.put("accionId", accionId);
            ordenToReport.put("accion", accion);
            ordenToReport.put("operacion", operacion);
            ordenToReport.put("cantidad", cantidad);
            ordenToReport.put("precio", precio);
            ordenToReport.put("fechaOperacion", fechaOperacion);
            ordenToReport.put("modo", modo);
    
            if (operacion.equals("VENTA")) {
                System.out.println("HERE");
                enoughStocks = validateClientStockService.validateClientStock(clienteId, accionId, cantidad);
                if (enoughStocks) {
                    ordenToReport.put("operacionExitosa", true);
                    ordenToReport.put("operacionObservaciones", "ok");
                    ordersToReportArray.add(ordenToReport);
                    ordenObject.put("status", "operacionExitosa");
                } else {
                    ordenObject.put("status", "operacionFallida");
                }
            } else {
                ordenToReport.put("operacionExitosa", true);
                ordenToReport.put("operacionObservaciones", "ok");
                ordersToReportArray.add(ordenToReport);
                ordenObject.put("status", "operacionExitosa");

            }

        }
        JSONObject ordersToReport = new JSONObject();
        ordersToReport.put("ordenes", ordersToReportArray);
        reportOrderService.reportOrden(ordersToReport);
         // aca deberia editar el estado de las ordenes las ordenes
        return orders;
    }


}
