package com.mycompany.myapp.service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetStockService {
    
    private final Logger log = LoggerFactory.getLogger(GetStockService.class);
    @Value("${spring.variables.base_url}")
    private String base_url;

    private HttpRequestService httpRequestService;

    public GetStockService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

     public boolean getStock(String codigo, Long id) {
        String url = base_url + "/acciones/buscar?codigo=" + codigo;
        JSONParser parser = new JSONParser();
        JSONArray acciones;

        ResponseEntity<String> response = httpRequestService.request(url, "GET", null);

        try {
            JSONObject responseObject = (JSONObject) parser.parse(response.getBody());
            acciones = (JSONArray) responseObject.get("acciones");

            for (Object accion: acciones) {
                JSONObject accionObject = (JSONObject) accion;
                Long accionId = (Long) accionObject.get("id");
                String accionCodigo = (String) accionObject.get("codigo");
                System.out.println(accionCodigo + codigo + " " + accionId + id);

                if (accionId == id && accionCodigo.equals(codigo)) {
                    return true;
                } 
            }
            return false;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
         }
    } 
}
