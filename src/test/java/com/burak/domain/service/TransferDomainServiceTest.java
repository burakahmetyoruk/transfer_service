package com.burak.domain.service;

import com.burak.TransferServiceApplication;
import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.exception.NegativeBalanceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TransferServiceApplication.class)
public class TransferDomainServiceTest {

    private static final String TRANSFERRER_ACCOUNT_NAME = "transferrerAccountName";
    private static final String TRANSFERRED_ACCOUNT_NAME = "transferredAccountName";

    @Autowired
    private TransferDomainService transferDomainService;

    @Autowired
    private AccountRepository accountRepository;


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
        Account transferrerAccount = accountRepository.findByName(TRANSFERRER_ACCOUNT_NAME);
        Account transferredAccount = accountRepository.findByName(TRANSFERRED_ACCOUNT_NAME);

        transferDomainService.transferMoney(transferrerAccount, transferredAccount, BigDecimal.TEN);

        Account updatedTransferrerAccount = accountRepository.findByName(TRANSFERRER_ACCOUNT_NAME);
        Account updatedTransferredAccount = accountRepository.findByName(TRANSFERRED_ACCOUNT_NAME);

        assertEquals(20, updatedTransferrerAccount.getBalance().intValue());
        assertEquals(20, updatedTransferredAccount.getBalance().intValue());
    }

    @Test(expected = NegativeBalanceException.class)
    public void should_not_transfer_money_when_transferrer_account_be_overdrawn() {
        Account transferrerAccount = accountRepository.findByName(TRANSFERRER_ACCOUNT_NAME);
        Account transferredAccount = accountRepository.findByName(TRANSFERRED_ACCOUNT_NAME);

        transferDomainService.transferMoney(transferrerAccount, transferredAccount, BigDecimal.valueOf(35));
    }

    @After
    public void tearDown() {
        accountRepository.deleteAll();
    }
}