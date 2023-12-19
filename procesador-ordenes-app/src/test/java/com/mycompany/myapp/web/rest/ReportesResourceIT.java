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
 * Test class for the ReportesResource REST controller.
 *
 * @see ReportesResource
 */
@IntegrationTest
class ReportesResourceIT {

    private MockMvc restMockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // ReportesResource reportesResource = new ReportesResource();
        // restMockMvc = MockMvcBuilders.standaloneSetup(reportesResource).build();
    }

    /**
     * Test ordenesProcesadas
     */
    @Test
    void testOrdenesProcesadas() throws Exception {
        restMockMvc.perform(get("/api/reportes/ordenes-procesadas")).andExpect(status().isOk());
    }

    /**
     * Test ordenesNoProcesadas
     */
    @Test
    void testOrdenesNoProcesadas() throws Exception {
        restMockMvc.perform(get("/api/reportes/ordenes-no-procesadas")).andExpect(status().isOk());
    }
}
