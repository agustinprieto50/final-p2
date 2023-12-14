package com.mycompany.myapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GetClientService {

    private final Logger log = LoggerFactory.getLogger(GetClientService.class);
    private HttpRequestService httpRequestService;

    public GetClientService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public boolean getClient(Long id) {
        ResponseEntity<String> response = httpRequestService.request("http://192.168.194.254:8000/api/clientes/" + id, "GET", null);
        int status = response.getStatusCodeValue();
        if (status == 200) {
            return true;
        } else {
            return false;
        }
    } 

}
