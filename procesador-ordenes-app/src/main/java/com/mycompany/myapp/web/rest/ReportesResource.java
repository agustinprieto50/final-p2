package com.mycompany.myapp.web.rest;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
                                        @RequestParam(required = false) String fechaInicio,
                                        @RequestParam(required = false) String fechaFin) {
        
        List<Orden> allOrdens = ordenRepository.findAll();
        log.debug(allOrdens.toString());

        log.info("REST request to get all Ordens");
        return allOrdens.stream()
                .filter(ord -> cliente == null || ord.getCliente().equals(cliente))
                .filter(ord -> accion == null || ord.getAccion().equals(accion))
                .filter(ord -> isWithinDateRange(ord.getFechaOperacion(), fechaInicio, fechaFin))
                .filter(ord -> ord.getEstado().equals("operacionExitosa"))
                .collect(Collectors.toList());
    }

    private boolean isWithinDateRange(String orderDate, String startDate, String endDate) {
        if (orderDate == null) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

            ZonedDateTime zonedOrderDate = ZonedDateTime.parse(orderDate, formatter);

            ZonedDateTime zonedStartDate = startDate != null && !startDate.isEmpty() ? ZonedDateTime.parse(startDate, formatter) : null;
            ZonedDateTime zonedEndDate = endDate != null && !endDate.isEmpty() ? ZonedDateTime.parse(endDate, formatter) : null;

            return (zonedStartDate == null || zonedOrderDate.isEqual(zonedStartDate) || zonedOrderDate.isAfter(zonedStartDate))
                    && (zonedEndDate == null || zonedOrderDate.isEqual(zonedEndDate) || zonedOrderDate.isBefore(zonedEndDate.plusDays(1)));
        } catch (DateTimeException e) {
            log.info("Error parsing date: " + e.getMessage());
            return false;
        }
    }

    // Logger instance for logging
    /**
     * GET ordenesNoProcesadas
     */
    @GetMapping("/ordenes-no-procesadas")
    public List<Orden> ordenesNoProcesadas(@RequestParam(required = false) Long cliente,
                                         @RequestParam(required = false) String accion,
                                         @RequestParam(required = false) String fecha) {
        
        List<Orden> allOrdens = ordenRepository.findAll();
        log.debug(allOrdens.toString());

        log.info("REST request to get all Ordens");
        return allOrdens.stream()
                .filter(ord -> cliente == null || ord.getCliente().equals(cliente))
                .filter(ord -> accion == null || ord.getAccion().equals(accion))
                .filter(ord -> fecha == null || ord.getFechaOperacion().equals(fecha))
                .filter(ord -> ord.getEstado().equals("scheduled") || ord.getEstado().equals("hasErrors"))
                .collect(Collectors.toList());
    }
}
