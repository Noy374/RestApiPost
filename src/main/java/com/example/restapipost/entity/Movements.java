package com.example.restapipost.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementId;

    @OneToOne
    @JoinColumn(
            name = "shipmentId",
            referencedColumnName =  "shipmentId"
    )
    private Shipments shipment;

    @ManyToOne
    @JoinColumn(
            name = "postId",
            referencedColumnName =  "postId"
    )
    private PostOffices postOffice;

    private Boolean arrival;

    private Boolean departure;

    private Boolean receiptByTheAddressee;
}
