package com.mycompany.myapp.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public GetPendingOrdersService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public JSONArray getPendingOrders () {
        // Use JSON.simple to parse the JSON string
        ResponseEntity<String> response = httpRequestService.request("http://192.168.194.254:8000/api/ordenes/ordenes", "GET", null);
        JSONArray ordersArray;
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
            ordersArray = (JSONArray) jsonObject.get("ordenes");
            return ordersArray;

             // Now you can work with the JSONObject
             // ...
         } catch (ParseException e) {
            e.printStackTrace();
            return null;
         }

        
    } 


}
