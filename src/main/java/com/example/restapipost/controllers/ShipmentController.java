package com.example.restapipost.controllers;


import com.example.restapipost.entity.Movements;
import com.example.restapipost.payload.request.ArrivalRequest;
import com.example.restapipost.payload.request.ShipmentRequest;
import com.example.restapipost.payload.response.HistoryResponse;
import com.example.restapipost.payload.response.MessageResponse;
import com.example.restapipost.payload.response.ShipmentResponse;
import com.example.restapipost.payload.response.StatusResponse;
import com.example.restapipost.services.MovementService;
import com.example.restapipost.services.ShipmentService;
import com.example.restapipost.validations.ResponseErrorValidation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
@AllArgsConstructor
public class ShipmentController {


    private final ShipmentService shipmentService;

    private final MovementService movementService;

    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("/creat")
    ResponseEntity<Object> addShipment(@Valid @RequestBody ShipmentRequest shipmentRequest, BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Long id=shipmentService.creatShipmentAndGetId(shipmentRequest);
        movementService.creatMovement(id);
        return ResponseEntity.ok(new ShipmentResponse(id));
    }

    @PostMapping("/{shipmentId}/arrival")
    ResponseEntity<Object> arrival(@PathVariable Long shipmentId,@Valid @RequestBody ArrivalRequest arrivalRequest, BindingResult bindingResult){
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        if(movementService.updatePostByShipmentId(arrivalRequest,shipmentId))
          return   ResponseEntity.ok(new MessageResponse("Data successful changed"));
       return ResponseEntity.badRequest().body(new MessageResponse("Incorrect data sent"));
    }

    @PostMapping("/{shipmentId}/departure")
    ResponseEntity<Object> departure(@PathVariable Long shipmentId){
       if( movementService.updateDepartureByShipmentId(shipmentId))
           return   ResponseEntity.ok(new MessageResponse("Data successful changed"));
        return ResponseEntity.badRequest().body(new MessageResponse("Incorrect data sent"));
    }


    @PostMapping("/{shipmentId}/delivery")
    ResponseEntity<Object> delivery(@PathVariable Long shipmentId){
        if( movementService.updateDeliveryByShipmentId(shipmentId))
            return   ResponseEntity.ok(new MessageResponse("Data successful changed"));
        return ResponseEntity.badRequest().body(new MessageResponse("Incorrect data sent"));
    }

    @GetMapping("/{shipmentId}/status")
    ResponseEntity<Object> status(@PathVariable Long shipmentId){
       if( movementService.getStatusByShipmentId(shipmentId))
           return   ResponseEntity.ok(new StatusResponse(true));
        return ResponseEntity.badRequest().body(new StatusResponse(false));
    }

    @GetMapping("/{shipmentId}/history")
    ResponseEntity<Object> history(@PathVariable Long shipmentId) {

        Movements history = movementService.getHistory(shipmentId);
        if(history==null)return ResponseEntity.badRequest().body(new MessageResponse("Incorrect data sent"));
        return ResponseEntity.ok(new HistoryResponse(
                history.getShipment().getType(),
                history.getShipment().getRecipientIndex(),
                history.getShipment().getAddressOfTheRecipient(),
                history.getShipment().getReceiverName(),
                history.getPostOffice().getBranchName(),
                history.getPostOffice().getBranchAddress(),
                history.getArrival(),
                history.getDeparture(),
                history.getReceiptByTheAddressee()));

    }

}
