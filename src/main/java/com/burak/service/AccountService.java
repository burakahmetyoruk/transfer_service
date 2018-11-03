package com.burak.service;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.exception.AccountAlreadyExistException;
import com.burak.exception.AccountNotFoundException;
import com.burak.model.account.AccountRequest;
import com.burak.model.account.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Account service class that responsible for creating account with initial balance
 *
 * @author byoruk
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    private final AccountRepository accountRepository;

    /**
     * @param accountRequest name of the account to be created.
     * @return accountResponse
     */
    public AccountResponse create(AccountRequest accountRequest) {
        Optional<Account> existingAccount = accountRepository.findByName(accountRequest.getAccountName());
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            logger.info("Account already exist With Name: {}", account.getName());
            throw new AccountAlreadyExistException(account.getName());
        }
        Account account = new Account(accountRequest.getAccountName());
        accountRepository.save(account);
        logger.info("Account Created With Name: {}", account.getName());
        return new AccountResponse(account.getId(), account.getName());
    }

    public AccountResponse retrieve(String accountName) {
        Account account = retrieveAccount(accountName);
        return new AccountResponse(account.getId(), account.getName());
    }

    void save(Account account) {
        accountRepository.save(account);
    }

    Account retrieveAccount(String accountName) {
        Optional<Account> account = accountRepository.findByName(accountName);
        return account.orElseThrow(() -> new AccountNotFoundException(accountName));
    }
}