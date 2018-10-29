package com.burak.service.transfer;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.domain.service.TransferDomainService;
import com.burak.exception.AccountNotFoundException;
import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransferDomainService transferDomainService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    public void should_transfer_money_from_one_account_to_another() throws Exception {
        String transferrerAccountName = "transferrerAccount";
        String transferredAccountName = "transferredAccount";

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferrerAccountName(transferrerAccountName);
        transferRequest.setTransferredAccountName(transferredAccountName);
        transferRequest.setTransferAmount(BigDecimal.TEN);

        Account transferrerAccount = new Account(transferrerAccountName);
        transferrerAccount.setBalance(BigDecimal.valueOf(30));

        Account transferredAccount = new Account(transferredAccountName);

        when(accountRepository.findByName(transferrerAccountName)).thenReturn(transferrerAccount);
        when(accountRepository.findByName(transferredAccountName)).thenReturn(transferredAccount);

        TransferResponse transferResponse = transferService.transferMoney(transferRequest);

        assertNotNull(transferResponse);
        assertNotNull(transferResponse.getOperationResult());
        assertEquals(0, transferResponse.getOperationResult().getReturnCode().intValue());

        //Verify that Transfer Money method call with true parameters.
        verify(transferDomainService, times(1)).transferMoney(transferrerAccount, transferredAccount, transferRequest.getTransferAmount());
        verify(accountRepository, times(1)).findByName(transferredAccountName);
        verify(accountRepository, times(1)).findByName(transferrerAccountName);
    }


    @Test(expected = AccountNotFoundException.class)
    public void should_not_transfer_money_when_transferred_account_not_found() {
        String transferrerAccountName = "transferrerAccount";
        String transferredAccountName = "transferredAccount";

        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferrerAccountName(transferrerAccountName);
        transferRequest.setTransferredAccountName(transferredAccountName);
        transferRequest.setTransferAmount(BigDecimal.valueOf(-1));

        when(accountRepository.findByName(transferrerAccountName)).thenReturn(null);

        transferService.transferMoney(transferRequest);
    }
}