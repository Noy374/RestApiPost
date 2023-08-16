package com.example.restapipost.payload.response;

import com.example.restapipost.entity.Movements;
import com.example.restapipost.entity.PostOffices;
import com.example.restapipost.entity.Shipments;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class HistoryResponse {
    private String type;

    private String recipientIndex;

    private String addressOfTheRecipient;

    private String receiverName;

    private String branchName;

    private String branchAddress;

    private Boolean arrival;

    private Boolean departure;

    private Boolean receiptByTheAddressee;
}
