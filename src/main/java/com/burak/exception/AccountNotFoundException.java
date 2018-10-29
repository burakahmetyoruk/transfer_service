package com.burak.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {

    private final String accountName;

    public AccountNotFoundException(String accountName) {
        super("Account Not Found with Name: " + accountName);
        this.accountName = accountName;
    }
}