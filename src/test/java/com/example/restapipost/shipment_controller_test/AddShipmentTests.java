package com.example.restapipost.shipment_controller_test;

import com.example.restapipost.payload.request.ShipmentRequest;
import com.example.restapipost.payload.response.ShipmentResponse;
import com.example.restapipost.services.MovementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddShipmentTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @MockBean
    private MovementService movementService;
    @Test
    public void testAddShipment() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ShipmentRequest request = new ShipmentRequest();
        request.setType("letter");
        request.setReceiverName("Oleg");
        request.setAddressOfTheRecipient("Voronezh,Vladimir Nevsky,1");
        request.setRecipientIndex("12345");
        HttpEntity<ShipmentRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/shipments/creat", HttpMethod.POST, entity, Object.class);

        assertEquals(200, response.getStatusCodeValue());

        ObjectMapper objectMapper = new ObjectMapper();
        ShipmentResponse shipmentResponse = objectMapper.convertValue(response.getBody(), ShipmentResponse.class);
        assertNotNull(shipmentResponse);
        assertNotNull(shipmentResponse.getShipmentId());
    }

    @Test
    public void testAddShipmentInvalidRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ShipmentRequest request = new ShipmentRequest();
        request.setType("letter");
        request.setReceiverName("Oleg");
        request.setAddressOfTheRecipient("Voronezh,Vladimir Nevsky,1");
        request.setRecipientIndex("");

        HttpEntity<ShipmentRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/shipments/creat", HttpMethod.POST, entity, Object.class);

        assertEquals(400, response.getStatusCodeValue());

        String errorMessage = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(errorMessage.contains("{NotEmpty=Recipient index cannot be empty!, recipientIndex=Recipient index cannot be empty!}"));
    }

}
