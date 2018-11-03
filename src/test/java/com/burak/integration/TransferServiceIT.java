package com.burak.integration;

import com.burak.TransferServiceApplication;
import com.burak.domain.entity.Account;
import com.burak.domain.entity.Transfer;
import com.burak.domain.repository.AccountRepository;
import com.burak.domain.repository.TransferRepository;
import com.burak.exception.NegativeBalanceException;
import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import com.burak.service.TransferService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TransferServiceApplication.class)
public class TransferServiceIT {

    private static final String TRANSFERRER_ACCOUNT_NAME = "transferrerAccountName";
    private static final String TRANSFERRED_ACCOUNT_NAME = "transferredAccountName";

    @Autowired
    private TransferService transferService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransferRepository transferRepository;


    @Before
    public void setUp() {
        Account transferrerAccount = new Account(TRANSFERRER_ACCOUNT_NAME);
        transferrerAccount.setBalance(BigDecimal.valueOf(30));
        accountRepository.save(transferrerAccount);

        Account transferredAccount = new Account(TRANSFERRED_ACCOUNT_NAME);
        accountRepository.save(transferredAccount);
    }

    @Test
    public void should_transfer_money_one_account_to_another() {
        Optional<Account> transferrerAccount = accountRepository.findByName(TRANSFERRER_ACCOUNT_NAME);
        Optional<Account> transferredAccount = accountRepository.findByName(TRANSFERRED_ACCOUNT_NAME);

        TransferRequest transferRequest = createTransferRequest(transferrerAccount.get(),
                transferredAccount.get(),
                BigDecimal.TEN);
        TransferResponse transferResponse = transferService.transfer(transferRequest);

        assertNotNull(transferResponse);
        assertNotNull(transferResponse.getOperationResult());
        assertEquals(0, transferResponse.getOperationResult().getReturnCode().intValue());

        Optional<Account> updatedTransferrerAccount = accountRepository.findByName(TRANSFERRER_ACCOUNT_NAME);
        Optional<Account> updatedTransferredAccount = accountRepository.findByName(TRANSFERRED_ACCOUNT_NAME);
        Optional<Transfer> transfer = transferRepository.findById(transferResponse.getTransferId());

        assertEquals(20, updatedTransferrerAccount.get().getBalance().intValue());
        assertEquals(20, updatedTransferredAccount.get().getBalance().intValue());
        assertEquals(transferrerAccount.get().getId(), transfer.get().getTransferrer().getId());
        assertEquals(transferredAccount.get().getId(), transfer.get().getTransferred().getId());
    }

    @Test(expected = NegativeBalanceException.class)
    public void should_not_transfer_money_when_transferrer_account_be_overdrawn() {
        Optional<Account> transferrerAccount = accountRepository.findByName(TRANSFERRER_ACCOUNT_NAME);
        Optional<Account> transferredAccount = accountRepository.findByName(TRANSFERRED_ACCOUNT_NAME);

        TransferRequest transferRequest = createTransferRequest(transferrerAccount.get(),
                transferredAccount.get(),
                BigDecimal.valueOf(35));
        transferService.transfer(transferRequest);
    }

    @After
    public void tearDown() {
        transferRepository.deleteAll();
        accountRepository.deleteAll();
    }

    private TransferRequest createTransferRequest(Account transferrer, Account transferred, BigDecimal amount) {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setTransferAmount(amount);
        transferRequest.setTransferrerAccountName(transferrer.getName());
        transferRequest.setTransferredAccountName(transferred.getName());
        return transferRequest;
    }
}