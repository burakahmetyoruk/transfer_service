package com.burak.service;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.TransferRepository;
import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import com.burak.service.TransferService;
import com.burak.service.AccountService;
import com.burak.service.LockService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private LockService lockService;

    @Test
    public void should_transfer_money_from_one_account_to_another() {
        String transferrerAccountName = "transferrerAccount";
        String transferredAccountName = "transferredAccount";

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferrerAccountName(transferrerAccountName);
        transferRequest.setTransferredAccountName(transferredAccountName);
        transferRequest.setTransferAmount(BigDecimal.TEN);

        Account transferrerAccount = new Account(transferrerAccountName);
        transferrerAccount.setBalance(BigDecimal.valueOf(30));

        Account transferredAccount = new Account(transferredAccountName);

        when(accountService.retrieveAccount(transferrerAccountName)).thenReturn(transferrerAccount);
        when(accountService.retrieveAccount(transferredAccountName)).thenReturn(transferredAccount);

        TransferResponse transferResponse = transferService.transfer(transferRequest);

        assertNotNull(transferResponse);
        assertNotNull(transferResponse.getOperationResult());
        assertEquals(0, transferResponse.getOperationResult().getReturnCode().intValue());
        assertEquals(BigDecimal.TEN, transferResponse.getTransferAmount());
        assertEquals(transferrerAccountName, transferResponse.getTransferrerName());
        assertEquals(transferredAccountName, transferResponse.getTransferredName());
    }
}