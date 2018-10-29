package com.burak.service.account;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.model.account.AccountDto;
import com.burak.model.account.AccountRequest;
import com.burak.model.account.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Account service class that responsible for creating account with initial balance
 * @author byoruk
 */
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    /**
     * @param accountRequest name of the account to be created.
     * @return accountResponse
     */
    public AccountResponse createAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest.getAccountName());
        accountRepository.save(account);
        AccountDto accountDto = convertToDto(account);
        return new AccountResponse(accountDto);
    }

    private AccountDto convertToDto(Account account) {
        return new AccountDto(account.getId(), account.getName());
    }
}