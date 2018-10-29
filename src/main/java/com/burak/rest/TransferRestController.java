package com.burak.rest;

import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import com.burak.service.transfer.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TransferRestController {

    private final TransferService transferService;

    @PostMapping(value = "/transfer-money")
    public ResponseEntity<TransferResponse> transferMoney(@RequestBody @Valid TransferRequest transferRequest) {
        TransferResponse transferResponse = transferService.transferMoney(transferRequest);
        return ResponseEntity.ok(transferResponse);
    }
}