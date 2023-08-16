package com.example.restapipost.shipment_controller_test;


import com.example.restapipost.entity.Movements;
import com.example.restapipost.entity.PostOffices;
import com.example.restapipost.entity.Shipments;
import com.example.restapipost.payload.request.ArrivalRequest;
import com.example.restapipost.payload.response.HistoryResponse;
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
public class HistoryTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private MovementService movementService;
    @Test
    public void testHistory()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        Movements history=new Movements();
        history.setShipment(new Shipments());
        history.setPostOffice(new PostOffices());
        history.getShipment().setType("letter");
                history.getShipment().setRecipientIndex("12345");
                history.getShipment().setAddressOfTheRecipient("Voronezh,Vladimir Nevsky,1");
                history.getShipment().setReceiverName("Oleg");
                history.getPostOffice().setBranchName("PostMe");
                history.getPostOffice().setBranchAddress("Voronezh,Vladimir Nevsky,3");
                history.setArrival(true);
                history.setDeparture(true);
                history.setReceiptByTheAddressee(true);
        when(movementService.getHistory( shipmentId)).thenReturn(history);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/history", HttpMethod.GET, entity, Object.class);
        String message = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(message.contains
                ("{type=letter, " +
                        "recipientIndex=12345," +
                        " addressOfTheRecipient=Voronezh,Vladimir Nevsky,1," +
                        " receiverName=Oleg, branchName=PostMe," +
                        " branchAddress=Voronezh,Vladimir Nevsky,3," +
                        " arrival=true, departure=true, receiptByTheAddressee=true}"));
    }



    @Test
    public void testHistoryInvalidSqlRequest()  {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Long shipmentId = 1L;
        when(movementService.getHistory( shipmentId)).thenReturn(null);
        HttpEntity<ArrivalRequest> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Object> response = restTemplate.
                exchange("/shipments/1/history", HttpMethod.GET, entity, Object.class);
        String errorMessage = Objects.requireNonNull(response.getBody()).toString();
        assertTrue(errorMessage.contains("{message=Incorrect data sent}"));
    }
}
