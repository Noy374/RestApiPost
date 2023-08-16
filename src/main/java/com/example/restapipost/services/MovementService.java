package com.example.restapipost.services;


import com.example.restapipost.entity.Movements;
import com.example.restapipost.entity.PostOffices;
import com.example.restapipost.entity.Shipments;
import com.example.restapipost.payload.request.ArrivalRequest;
import com.example.restapipost.repositorys.MovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MovementService {
    private final MovementRepository movementRepository;
    private final ShipmentService shipmentService;

    private final PostService postService;
    public void creatMovement(Long id) {
        Movements movement =new Movements();
        if(shipmentService.getShipmentById(id)==null) ;
        movement.setShipment(shipmentService.getShipmentById(id));
        movement.setArrival(false);
        movement.setDeparture(false);
        movement.setReceiptByTheAddressee(false);
        movementRepository.save(movement);
    }


    public boolean updatePostByShipmentId(ArrivalRequest arrivalRequest,Long shipmentId) {
        PostOffices postById = postService.getPostById(arrivalRequest.getId());
        Shipments shipmentById = shipmentService.getShipmentById(shipmentId);
        if(postById==null || shipmentById==null)return false;
        movementRepository.updatePostByShipmentId
                ( postById, shipmentById);
        return true;
    }

    public boolean updateDepartureByShipmentId(Long shipmentId) {
        Shipments shipmentById = shipmentService.getShipmentById(shipmentId);

        if(shipmentById==null)return  false;
        movementRepository.updateDepartureByShipmentId(shipmentById);

        return true;
    }

    public boolean updateDeliveryByShipmentId(Long shipmentId) {
        Shipments shipmentById = shipmentService.getShipmentById(shipmentId);

        if(shipmentById==null)return  false;
        movementRepository.updateDeliveryByShipmentId(shipmentById);

        return true;
    }

    public boolean getStatusByShipmentId(Long shipmentId) {
        return movementRepository.
                getMovementsByShipment(shipmentService.getShipmentById(shipmentId)).getReceiptByTheAddressee();
    }

    public Movements getHistory(Long shipmentId) {
        Shipments shipmentById = shipmentService.getShipmentById(shipmentId);

        if(shipmentById==null)return  null;

        return movementRepository.getMovementsByShipment(shipmentById);
    }
}
