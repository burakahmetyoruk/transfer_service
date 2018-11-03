package com.burak.rest;

import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import com.burak.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TransferRestController {

    private final TransferService transferService;

    @PostMapping(value = "/transfers")
    public ResponseEntity<TransferResponse> transfer(@RequestBody @Valid TransferRequest transferRequest) {
        TransferResponse transferResponse = transferService.transfer(transferRequest);
        return ResponseEntity.ok(transferResponse);
    }
}