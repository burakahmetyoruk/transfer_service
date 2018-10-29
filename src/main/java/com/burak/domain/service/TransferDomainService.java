package com.burak.domain.service;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.exception.NegativeBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Transfer money from one account to another
 * @author byoruk
 */
@Service
@RequiredArgsConstructor
public class TransferDomainService {

    private final AccountRepository accountRepository;

    /**
     * @param transferrerAccount  Transferrer Account
     * @param transferredAccount  Transferred Account
     * @param transferAmount    Transfer Amount
     * @throws NegativeBalanceException
     */
    @Transactional
    public void transferMoney(Account transferrerAccount, Account transferredAccount, final BigDecimal transferAmount) {
        //Account must not be overdrawn
        if (transferrerAccount.getBalance().compareTo(transferAmount) < 0) {
            throw new NegativeBalanceException(transferrerAccount.getName());
        }
        transferrerAccount.decreaseBalance(transferAmount);
        accountRepository.save(transferrerAccount);

        transferredAccount.increaseBalance(transferAmount);
        accountRepository.save(transferredAccount);
    }
}