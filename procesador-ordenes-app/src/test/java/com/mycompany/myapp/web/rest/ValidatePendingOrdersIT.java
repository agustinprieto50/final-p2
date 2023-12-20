package com.mycompany.myapp.web.rest;


import com.mycompany.myapp.service.OrdersValidationResultService;
import com.mycompany.myapp.service.GetClientService;
import com.mycompany.myapp.service.GetStockService;
import com.mycompany.myapp.service.ValidatePendingOrdersService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ValidatePendingOrdersIT {

    @InjectMocks
    private ValidatePendingOrdersService validatePendingOrdersService;

    @Mock
    private GetClientService mockClientService;

    @Mock
    private GetStockService mockStockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testValidatePendingOrders() {
        JSONArray ordersArray = new JSONArray();
        JSONObject order1 = createOrder(26351L, 13L, "PAM", "COMPRA", 127.78, 20, "2023-12-20T04:00:00Z", "PRINCIPIODIA");
        JSONObject order2 = createOrder(26351L, 13L, "PAM", "COMPRA", 119.17, 32, "2023-12-20T12:00:00Z", "AHORA");
        JSONObject order3 = createOrder(26351L, 13L, "PAM", "VENTA", 122.96, 12, "2023-12-20T14:20:00Z", "AHORA");
        JSONObject order4 = createOrder(26351L, 13L, "PAM", "VENTA", 118.23, 10, "2023-12-20T18:00:00Z", "FINDIA");
        JSONObject order5 = createOrder(2L, 176L, "NONE", "VENTA", 118.23, 10, "2023-12-20T18:00:00Z", "FINDIA");

        ordersArray.add(order1);
        ordersArray.add(order2);
        ordersArray.add(order3);
        ordersArray.add(order4);
        ordersArray.add(order5);

        
        when(mockClientService.getClient(26351L)).thenReturn(true);
        when(mockStockService.getStock("PAM", 13L)).thenReturn(true);
        when(mockClientService.getClient(176L)).thenReturn(false);
        when(mockStockService.getStock("PAM", 2L)).thenReturn(false);

        OrdersValidationResultService result = validatePendingOrdersService.validatePendingOrders(ordersArray);

       
        assertEquals(4, result.getValidatedOrders().size());
        assertEquals(1, result.getIncompleteOrders().size());


       
    }

    private JSONObject createOrder(Long clienteId, Long accionId, String accionCodigo, String operacion, double precio,
    int cantidad, String fechaOperacion, String modo) {
        JSONObject order = new JSONObject();
        order.put("cliente", clienteId);
        order.put("accionId", accionId);
        order.put("accion", accionCodigo);
        order.put("operacion", operacion);
        order.put("precio", precio);
        order.put("cantidad", cantidad);
        order.put("fechaOperacion", fechaOperacion);
        order.put("modo", modo);
        return order;
        }
}
