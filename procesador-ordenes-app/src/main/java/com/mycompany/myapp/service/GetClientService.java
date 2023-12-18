package com.mycompany.myapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GetClientService {

    private final Logger log = LoggerFactory.getLogger(GetClientService.class);

    @Value("${spring.variables.base_url}")
    private String base_url;

    private HttpRequestService httpRequestService;

    public GetClientService(HttpRequestService httpRequestService) {
        this.httpRequestService = httpRequestService;
    }

    public boolean getClient(Long id) {
        String url = base_url + "/clientes/" + id;
        ResponseEntity<String> response = httpRequestService.request(url, "GET", null);
        int status = response.getStatusCodeValue();
        if (status == 200) {
            return true;
        } else {
            return false;
        }
    } 

}
