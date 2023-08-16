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
public class ArrivalTests {

    @Autowired
    private TestRestTemplate restTemplate;


    @MockBean
    private MovementService movementService;
    @Test
    public void testArrival()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        ArrivalRequest arrivalRequest = new ArrivalRequest();
        arrivalRequest.setBranchAddress("Voronezh,Vladimir Nevsky,1");
        arrivalRequest.setId(1L);
        arrivalRequest.setBranchName("PostMe");
        when(movementService.updatePostByShipmentId(arrivalRequest, shipmentId)).thenReturn(true);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(arrivalRequest, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/arrival", HttpMethod.POST, entity, Object.class);

        String message = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(message.contains("{message=Data successful changed}"));
    }

    @Test
    public void testArrivalInvalidRequest()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        ArrivalRequest arrivalRequest = new ArrivalRequest();
        arrivalRequest.setBranchAddress("Voronezh,Vladimir Nevsky,1");
        arrivalRequest.setId(1L);
        arrivalRequest.setBranchName("");
        when(movementService.updatePostByShipmentId(arrivalRequest, shipmentId)).thenReturn(true);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(arrivalRequest, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/arrival", HttpMethod.POST, entity, Object.class);

        String errorMessage = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(errorMessage.contains("{branchName=Branch name  cannot be empty!, NotEmpty=Branch name  cannot be empty!}"));
    }

    @Test
    public void testArrivalInvalidSqlRequest()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        ArrivalRequest arrivalRequest = new ArrivalRequest();
        arrivalRequest.setBranchAddress("Voronezh,Vladimir Nevsky,1");
        arrivalRequest.setId(1L);
        arrivalRequest.setBranchName("PostMe");
        when(movementService.updatePostByShipmentId(arrivalRequest, shipmentId)).thenReturn(false);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(arrivalRequest, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/shipments/1/arrival", HttpMethod.POST, entity, Object.class);

        String errorMessage = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(errorMessage.contains("{message=Incorrect data sent}"));
    }
}
