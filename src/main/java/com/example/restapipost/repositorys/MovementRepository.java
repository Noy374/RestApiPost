package com.example.restapipost.repositorys;

import com.example.restapipost.entity.Movements;
import com.example.restapipost.entity.PostOffices;
import com.example.restapipost.entity.Shipments;
import com.example.restapipost.payload.request.ArrivalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MovementRepository extends JpaRepository<Movements,Long> {


    @Modifying
    @Query("UPDATE Movements u SET u.postOffice = :postOffice WHERE u.shipment= :shipment")
    @Transactional
    void updatePostByShipmentId(@Param("postOffice")PostOffices postOffice,@Param("shipment") Shipments shipment);

    @Modifying
    @Query("UPDATE Movements u SET u.departure = true WHERE u.shipment= :shipment")
    @Transactional
    void updateDepartureByShipmentId(@Param("shipment")Shipments shipment);

    @Modifying
    @Query("UPDATE Movements u SET u.receiptByTheAddressee = true WHERE u.shipment= :shipment")
    @Transactional
    void updateDeliveryByShipmentId(@Param("shipment")Shipments shipment);
     Movements getMovementsByShipment(Shipments shipment);
}
