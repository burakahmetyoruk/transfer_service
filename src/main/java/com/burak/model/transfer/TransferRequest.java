package com.burak.model.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Setter
@Getter
public class TransferRequest {

    @NotBlank(message = "TransferrerAccountName must not be empty")
    @JsonProperty(value = "transferrer-account-name")
    private String transferrerAccountName;

    @NotBlank(message = "TransferredAccountName must not be empty")
    @JsonProperty(value = "transferred-account-name")
    private String transferredAccountName;

    @JsonProperty(value = "transfer-amount")
    @DecimalMin("1")
    private BigDecimal transferAmount;
}