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
public class StatusTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private MovementService movementService;
    @Test
    public void testStatus()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;

        when(movementService.getStatusByShipmentId( shipmentId)).thenReturn(true);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/status", HttpMethod.GET, entity, Object.class);
        String message = Objects.requireNonNull(response.getBody()).toString();
        System.out.println(message);
        assertTrue(message.contains("{status=true}"));
    }



    @Test
    public void testStatusInvalidRequest()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;

        when(movementService.getStatusByShipmentId( shipmentId)).thenReturn(false);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/status", HttpMethod.GET, entity, Object.class);
        String message = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(message.contains("{status=false}"));
    }
}
