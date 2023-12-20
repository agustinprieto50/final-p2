package com.mycompany.myapp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.myapp.domain.Orden;

@Service
@Transactional
public class ReportesService {

    private final Logger log = LoggerFactory.getLogger(ReportesService.class);
    private HttpRequestService httpRequestService;

    public ReportesService(
        HttpRequestService httpRequestService
    ) {
        this.httpRequestService = httpRequestService;
    }

}
