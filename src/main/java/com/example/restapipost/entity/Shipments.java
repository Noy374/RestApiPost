package com.example.restapipost.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Shipments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shipmentId;


    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String recipientIndex;

    @Column(nullable = false)
    private String addressOfTheRecipient;

    @Column(nullable = false)
    private String receiverName;

    @OneToOne(mappedBy = "shipment")
    Movements movement;

}
