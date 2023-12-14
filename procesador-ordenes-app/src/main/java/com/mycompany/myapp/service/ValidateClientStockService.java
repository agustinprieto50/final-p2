package com.mycompany.myapp.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ValidateClientStockService {

    private final Logger log = LoggerFactory.getLogger(ValidateClientStockService.class);
    private HttpRequestService httpRequestService;


    public ValidateClientStockService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public boolean validateClientStock(Long clienteId, Long accionId, Long cantidad) {
        JSONParser parser = new JSONParser();

        String url = "http://192.168.194.254:8000/api/reporte-operaciones/consulta_cliente_accion?clienteId=" + clienteId + "&accionId=" + accionId;
        ResponseEntity<String> response = httpRequestService.request(url , "GET", null);

        try {
            JSONObject responseObject = (JSONObject) parser.parse(response.getBody());
            Long cantidadActual = (Long) responseObject.get("cantidadActual");
            if (cantidadActual != null) {
                if (cantidadActual - cantidad >= 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        

    }

}
