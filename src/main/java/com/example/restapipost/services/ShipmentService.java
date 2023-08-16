package com.example.restapipost.services;


import com.example.restapipost.entity.Shipments;
import com.example.restapipost.payload.request.ShipmentRequest;
import com.example.restapipost.repositorys.ShipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public Long creatShipmentAndGetId(ShipmentRequest shipmentRequest) {
        Shipments shipment=new Shipments();
        shipment.setReceiverName(shipmentRequest.getReceiverName());
        shipment.setRecipientIndex(shipmentRequest.getRecipientIndex());
        shipment.setType(shipmentRequest.getType());
        shipment.setAddressOfTheRecipient(shipmentRequest.getAddressOfTheRecipient());
        shipmentRepository.save(shipment);
        return shipment.getShipmentId();
    }

    public Shipments getShipmentById(Long id) {
        if(shipmentRepository.findById(id).isEmpty())return null;
          return shipmentRepository.findById(id).get();
    }
}
