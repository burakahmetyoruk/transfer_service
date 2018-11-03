package com.burak.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class AccountRequest {

    @JsonProperty(value = "account_name")
    @NotBlank(message = "Account name must not be empty")
    private String accountName;
}