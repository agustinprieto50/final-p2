package com.mycompany.myapp.service;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.RequestEntity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.swagger.v3.oas.models.parameters.RequestBody;

@Service
@Transactional
public class ReportOrderService {

    private final Logger log = LoggerFactory.getLogger(ReportOrderService.class);
    private final HttpRequestService httpRequestService;

    public ReportOrderService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    } 

    public boolean reportOrden(JSONObject body) {
        System.out.println(body + " BODY");
        ResponseEntity<String> response = httpRequestService.request("http://192.168.194.254:8000/api/reporte-operaciones/reportar", "POST", body.toString());
        int status = response.getStatusCodeValue();
        if (status == 200) {
            return true;
        } else {
            return false;
        }
    } 


}
