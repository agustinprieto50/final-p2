package com.mycompany.myapp.service;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ReportOrderService {

    private final Logger log = LoggerFactory.getLogger(ReportOrderService.class);
    private final HttpRequestService httpRequestService;
    @Value("${spring.variables.base_url_catedra}")
    private String base_url;

    @Value("${spring.variables.api_token_catedra}")
    private String token;

    public ReportOrderService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    } 

    public boolean reportOrden(JSONObject body) {
        String url = base_url + "/reporte-operaciones/reportar";
        // parametrizar urls
        ResponseEntity<String> response = httpRequestService.request(url, "POST", body.toString(), token);
        int status = response.getStatusCodeValue();
        if (status == 200) {
            return true;
        } else {
            return false;
        }
    } 


}
