package com.burak.model.base;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OperationResult {

    @JsonProperty(value = "return_code")
    private Integer returnCode;

    @JsonProperty(value = "message")
    private String message;
}