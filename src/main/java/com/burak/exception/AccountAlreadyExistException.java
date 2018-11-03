package com.burak.exception;

import lombok.Getter;

@Getter
public class AccountAlreadyExistException extends RuntimeException {

    private final String accountName;

    public AccountAlreadyExistException(String accountName) {
        super("Account Already Exist with Name: " + accountName);
        this.accountName = accountName;
    }
}