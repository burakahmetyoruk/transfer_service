package com.burak.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {

    @JsonProperty(value = "account-id")
    private Long id;

    @JsonProperty(value = "account-name")
    private String name;
}
