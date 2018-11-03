package com.burak.rest;

import com.burak.model.account.AccountRequest;
import com.burak.model.account.AccountResponse;
import com.burak.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AccountRestController {

    private final AccountService accountService;

    @PostMapping(value = "/accounts")
    public ResponseEntity<Void> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        AccountResponse accountResponse = accountService.create(accountRequest);
        return new ResponseEntity<>(retrieveHttpHeaders(accountResponse.getName()),
                HttpStatus.CREATED);
    }

    @GetMapping(value = "/accounts/{name}")
    public ResponseEntity<AccountResponse> retrieveAccount(@PathVariable(value = "name") String name) {
        AccountResponse accountResponse = accountService.retrieve(name);
        return ResponseEntity.ok(accountResponse);
    }

    private HttpHeaders retrieveHttpHeaders(String accountName) {
        final URI location = ServletUriComponentsBuilder
                .fromCurrentServletMapping().path("/accounts/{name}").build()
                .expand(accountName).toUri();

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return headers;
    }
}