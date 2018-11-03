package com.burak.model.transfer;

import com.burak.model.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse extends BaseResponse {

    @JsonProperty(value = "transfer_id")
    private Long transferId;

    private Long created;

    @JsonProperty(value = "transfer_amount")
    private BigDecimal transferAmount;

    @JsonProperty(value = "transferrer_name")
    private String transferrerName;

    @JsonProperty(value = "transferred_name")
    private String transferredName;
}