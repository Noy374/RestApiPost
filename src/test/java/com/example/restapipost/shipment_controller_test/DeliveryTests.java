package com.example.restapipost.shipment_controller_test;


import com.example.restapipost.payload.request.ArrivalRequest;
import com.example.restapipost.services.MovementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeliveryTests {
    @Autowired
    private TestRestTemplate restTemplate;


    @MockBean
    private MovementService movementService;
    @Test
    public void testDelivery()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        when(movementService.updateDeliveryByShipmentId( shipmentId)).thenReturn(true);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/delivery", HttpMethod.POST, entity, Object.class);

        String message = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(message.contains("{message=Data successful changed}"));
    }



    @Test
    public void testDeliveryInvalidSqlRequest()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        when(movementService.updateDeliveryByShipmentId( shipmentId)).thenReturn(false);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/delivery", HttpMethod.POST, entity, Object.class);
        String errorMessage = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(errorMessage.contains("{message=Incorrect data sent}"));
    }
}
