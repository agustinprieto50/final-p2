package com.mycompany.myapp.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.mycompany.myapp.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Test class for the HTTPResource REST controller.
 *
 * @see HTTPResource
 */
@IntegrationTest
class HTTPResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        HTTPResource hTTPResource = new HTTPResource();
        restMockMvc = MockMvcBuilders.standaloneSetup(hTTPResource).build();
    }

    /**
     * Test getOrder
     */
    @Test
    void testGetOrder() throws Exception {
        restMockMvc.perform(get("/api/http/get-order")).andExpect(status().isOk());
    }

    /**
     * Test getAccion
     */
    @Test
    void testGetAccion() throws Exception {
        restMockMvc.perform(get("/api/http/get-accion")).andExpect(status().isOk());
    }

    /**
     * Test getCliente
     */
    @Test
    void testGetCliente() throws Exception {
        restMockMvc.perform(get("/api/http/get-cliente")).andExpect(status().isOk());
    }
}
