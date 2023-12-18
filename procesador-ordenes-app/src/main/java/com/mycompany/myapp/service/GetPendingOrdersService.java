package com.mycompany.myapp.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.mycompany.myapp.service.HttpRequestService;


@Service
@Transactional
public class GetPendingOrdersService {

    private final Logger log = LoggerFactory.getLogger(GetPendingOrdersService.class);
    private final HttpRequestService httpRequestService;
    
    @Value("${spring.variables.base_url}")
    private String base_url;

    public GetPendingOrdersService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public JSONArray getPendingOrders () {
        String url = base_url + "/ordenes/ordenes";
        ResponseEntity<String> response = httpRequestService.request(url, "GET", null);
        JSONArray ordersArray;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
            ordersArray = (JSONArray) jsonObject.get("ordenes");
            return ordersArray;

       
         } catch (ParseException e) {
            e.printStackTrace();
            return null;
         }

        
    } 


}
