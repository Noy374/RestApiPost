package com.example.restapipost.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ShipmentRequest {

    @NotEmpty(message = "Type  cannot be empty!")
    private String type;

    @NotEmpty(message = "Recipient index cannot be empty!")
    private String recipientIndex;

    @NotEmpty(message = "Address of the recipient  cannot be empty!")
    private String addressOfTheRecipient;

    @NotEmpty(message = "Receiver name  cannot be empty!")
    private String receiverName;
}
