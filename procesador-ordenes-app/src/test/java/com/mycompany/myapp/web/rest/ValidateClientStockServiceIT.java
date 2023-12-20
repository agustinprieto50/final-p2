package com.mycompany.myapp.web.rest;


import com.mycompany.myapp.service.HttpRequestService;
import com.mycompany.myapp.service.ValidateClientStockService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ValidateClientStockServiceIT {

    @InjectMocks
    private ValidateClientStockService validateClientStockService;

    @Mock
    private HttpRequestService mockHttpRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidateClientStockSufficientQuantity() throws ParseException {
        // Mocking external service response
        String responseBody = "{\"cantidadActual\": 15}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(mockHttpRequestService.request(any(), any(), any(), any())).thenReturn(mockResponse);

        // Call the method under test
        boolean result = validateClientStockService.validateClientStock(123L, 456L, 10L);

        // Validate the result
        assertTrue(result);
    }

    @Test
    void testValidateClientStockInsufficientQuantity() throws ParseException {
        // Mocking external service response
        String responseBody = "{\"cantidadActual\": 5}";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(mockHttpRequestService.request(any(), any(), any(), any())).thenReturn(mockResponse);

        // Call the method under test
        boolean result = validateClientStockService.validateClientStock(123L, 456L, 10L);

        // Validate the result
        assertFalse(result);
    }

    @Test
    void testValidateClientStockInvalidResponse() throws ParseException {
        // Mocking external service response
        String responseBody = "Invalid JSON response";
        ResponseEntity<String> mockResponse = new ResponseEntity<>(responseBody, HttpStatus.OK);
        when(mockHttpRequestService.request(any(), any(), any(), any())).thenReturn(mockResponse);

        // Call the method under test
        boolean result = validateClientStockService.validateClientStock(123L, 456L, 10L);

        // Validate the result
        assertFalse(result);
    }
}
