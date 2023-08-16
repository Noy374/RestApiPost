package com.example.restapipost.payload.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ArrivalRequest {

    @NotNull(message = "Id  cannot be empty!")
    Long id;
    @NotEmpty(message = "Branch name  cannot be empty!")
    private String branchName;

    @NotEmpty(message = "Branch address  cannot be empty!")
    private String branchAddress;
}
