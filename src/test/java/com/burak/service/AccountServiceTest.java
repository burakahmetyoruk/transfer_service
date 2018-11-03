package com.burak.service;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.exception.AccountNotFoundException;
import com.burak.model.account.AccountRequest;
import com.burak.model.account.AccountResponse;
import com.burak.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void should_create_account_with_initial_balance() {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountName("accountName");

        AccountResponse accountResponse = accountService.create(accountRequest);

        assertNotNull(accountResponse);
        assertNotNull(accountResponse.getOperationResult());
        assertEquals(0, accountResponse.getOperationResult().getReturnCode().intValue());

        Account account = new Account();
        account.setName(accountRequest.getAccountName());
        //Initial Balance
        account.setBalance(BigDecimal.TEN);
        //To verify -> call repository method with true Account object
        verify(accountRepository).save((Account) argThat(new ReflectionEquals(account, "id")));
    }

    @Test(expected = AccountNotFoundException.class)
    public void should_throw_account_not_found_exception_when_account_does_not_exist() {
        String transferrerAccountName = "transferrerAccount";

        when(accountRepository.findByName(transferrerAccountName)).thenReturn(Optional.empty());

        accountService.retrieve(transferrerAccountName);
    }
}