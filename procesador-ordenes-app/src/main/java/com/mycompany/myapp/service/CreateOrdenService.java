package com.mycompany.myapp.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class CreateOrdenService {

    private final Logger log = LoggerFactory.getLogger(CreateOrdenService.class);
    private final OrdenRepository ordenRepository;

    public CreateOrdenService(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    public List<Orden> saveAllFromJsonArray(JSONArray ordenes) {
        List<Orden> ordenList = new ArrayList<>();

        for (Object object : ordenes) {
            JSONObject ordenObject = (JSONObject) object;

            Orden orden = new Orden();
            orden.setCliente( (Long) ordenObject.get("cliente"));
            orden.setAccionId( (Long) ordenObject.get("accionId"));
            orden.setAccion( (String) ordenObject.get("accion"));
            orden.setOperacion( (String) ordenObject.get("operacion"));
            orden.setPrecio( (Double) ordenObject.get("precio"));
            orden.setCantidad( (Long) ordenObject.get("cantidad"));
            orden.setFechaOperacion( (String) ordenObject.get("fechaOperacion"));
            orden.setModo( (String) ordenObject.get("modo"));
            orden.setEstado( (String) ordenObject.get("status"));



            ordenList.add(orden);
        }

        // Save all Orden objects using saveAll
        return ordenRepository.saveAll(ordenList);
    }

}
