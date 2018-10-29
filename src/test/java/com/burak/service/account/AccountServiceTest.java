package com.burak.service.account;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.model.account.AccountRequest;
import com.burak.model.account.AccountResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void should_create_account_with_initial_balance() throws Exception {
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountName("accountName");

        AccountResponse accountResponse = accountService.createAccount(accountRequest);

        assertNotNull(accountResponse);
        assertNotNull(accountResponse.getOperationResult());
        assertEquals(0, accountResponse.getOperationResult().getReturnCode().intValue());

        Account account = new Account();
        account.setName(accountRequest.getAccountName());
        //Initial Balance
        account.setBalance(BigDecimal.TEN);
        //To verify -> call repository method with true Account object
        verify(accountRepository, times(1)).save((Account) argThat(new ReflectionEquals(account, "id", "version")));
    }
}