package com.mycompany.myapp.service;

import org.springframework.transaction.annotation.Transactional;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.json.simple.parser.ParseException;


@Service
@Transactional
public class GetValorActual {

    @Autowired
    private HttpRequestService httpRequestService;


    @Value("${spring.variables.base_url_catedra}")
    private String base_url;

    @Value("${spring.variables.api_token_catedra}")
    private String token;

    public Double getValorActual(String codigo){


        String url = base_url + "/acciones/ultimovalor/" + codigo;

        JSONParser jsonParser = new JSONParser();

        ResponseEntity<String> response = httpRequestService.request(url, "GET", null, token);
        String responseBody = response.getBody();

        try {
            JSONObject jsonResponse = (JSONObject) jsonParser.parse(responseBody);
        
            // Extract the "ultimoValor" field and then the "valor" field
            JSONObject ultimoValor = (JSONObject) jsonResponse.get("ultimoValor");
            Double valor = (Double) ultimoValor.get("valor");
        
            return valor;

        } catch (ParseException e) {
            // Handle the parse exception (e.g., log it or throw a custom exception)
            e.printStackTrace();
            return null;
        }

    }
}
