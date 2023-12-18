package com.mycompany.myapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import com.mycompany.myapp.domain.Orden;
import com.mycompany.myapp.repository.OrdenRepository;

@Service
@Transactional
public class UpdateScheduledOrdersService {

    private final Logger log = LoggerFactory.getLogger(UpdateScheduledOrdersService.class);
    private final OrdenRepository ordenRepository;

    public UpdateScheduledOrdersService(
        OrdenRepository ordenRepository
    ) {
        this.ordenRepository = ordenRepository;
    }

    public void updateScheduledOrders(List<Long> ids) {
        List<Orden> orders = ordenRepository.findAllById(ids);
        for (Orden order : orders) {
            order.setEstado("operacionExitosa");
        }
        ordenRepository.saveAll(orders);
    }
}
