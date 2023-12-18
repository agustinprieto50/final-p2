package com.mycompany.myapp.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Orden;
import com.mycompany.myapp.repository.OrdenRepository;

@Service
@Transactional
public class GetScheduledOrdersService {

    private final Logger log = LoggerFactory.getLogger(GetScheduledOrdersService.class);
    private OrdenRepository ordenRepository;

    public GetScheduledOrdersService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    public JSONArray getScheduledOrders() {
        JSONArray scheduledOrdersArray = new JSONArray();

        List<Orden> scheduledOrders = ordenRepository.findAll();

        for (Orden orden : scheduledOrders) {
            if (orden.getEstado().equals("scheduled")) {
                JSONObject orderObject = this.convertToJSONObject(orden);
                String estado = (String) orderObject.get("estado");
                if (estado.equals("scheduled")) {
                    scheduledOrdersArray.add(orderObject);
                }
            }
        }

        return scheduledOrdersArray;

    }

    public JSONObject convertToJSONObject(Orden orden) {
        JSONObject orderObject = new JSONObject();

        orderObject.put("id", orden.getId());
        orderObject.put("cliente", orden.getCliente());
        orderObject.put("accionId", orden.getAccionId());
        orderObject.put("accion", orden.getAccion());
        orderObject.put("operacion", orden.getOperacion());
        orderObject.put("precio", orden.getPrecio());
        orderObject.put("cantidad", orden.getCantidad());
        orderObject.put("fechaOperacion", orden.getFechaOperacion());
        orderObject.put("modo", orden.getModo());
        orderObject.put("estado", orden.getEstado());

        return orderObject;
        

    }

}
