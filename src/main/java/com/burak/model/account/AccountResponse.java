package com.burak.model.account;


import com.burak.model.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse extends BaseResponse {

    @JsonProperty(value = "data")
    private AccountDto accountDto;
}