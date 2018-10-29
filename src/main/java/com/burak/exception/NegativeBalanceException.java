package com.burak.exception;

import lombok.Getter;

@Getter
public class NegativeBalanceException extends RuntimeException {

    private final String accountName;

    public NegativeBalanceException(String accountName) {
        super(accountName + " balance must not be negative!");
        this.accountName = accountName;
    }
}