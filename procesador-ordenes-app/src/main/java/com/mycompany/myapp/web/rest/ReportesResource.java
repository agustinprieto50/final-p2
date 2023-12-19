package com.mycompany.myapp.web.rest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mycompany.myapp.repository.OrdenRepository;


import com.mycompany.myapp.domain.Orden;

/**
 * ReportesResource controller
 */
@RestController
@RequestMapping("/api/reportes")
public class ReportesResource {

    private final Logger log = LoggerFactory.getLogger(ReportesResource.class);
    private OrdenRepository ordenRepository;

    public ReportesResource(OrdenRepository ordenRepository) {
        this.ordenRepository = ordenRepository;
    }

    /**
     * GET ordenesProcesadas
     */
    @GetMapping("/ordenes-procesadas")
    public List<Orden> ordenesProcesadas(@RequestParam(required = false) Long cliente,
                                         @RequestParam(required = false) String accion,
                                         @RequestParam(required = false) String fecha) {
        
        List<Orden> allOrdens = ordenRepository.findAll();
        log.debug(allOrdens.toString());

        log.info("REST request to get all Ordens");
        return allOrdens.stream()
                .filter(ord -> cliente == null || ord.getCliente().equals(cliente))
                .filter(ord -> accion == null || ord.getAccion().equals(accion))
                .filter(ord -> fecha == null || ord.getFechaOperacion().equals(fecha))
                .filter(ord -> ord.getEstado().equals("operacionExitosa"))
                .collect(Collectors.toList());
    }

    /**
     * GET ordenesNoProcesadas
     */
    @GetMapping("/ordenes-no-procesadas")
    public String ordenesNoProcesadas() {
        return "ordenesNoProcesadas";
    }
}
