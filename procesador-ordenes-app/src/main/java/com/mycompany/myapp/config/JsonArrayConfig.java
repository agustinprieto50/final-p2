package com.mycompany.myapp.config;

import org.json.simple.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonArrayConfig {
    @Bean
    public JSONArray jsonArray() {
        return new JSONArray();
    }
}
