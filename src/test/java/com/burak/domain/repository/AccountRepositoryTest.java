package com.burak.domain.repository;

import com.burak.TransferServiceApplication;
import com.burak.domain.entity.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TransferServiceApplication.class)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Before
    public void setUp(){
        accountRepository.save(new Account("account-1"));
    }

    /**
     * Tests the situation, that there are two concurrent users working with a web application
     * and both operating on the same account data at the same time.
     */
    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void should_operate_optimize_lock_successfully() {
        final String accountName = "account-1";

        Account accountForUserOne = accountRepository.findByName(accountName);
        Account accountForUserTwo = accountRepository.findByName(accountName);

        accountForUserOne.setBalance(BigDecimal.valueOf(30));
        accountForUserTwo.setBalance(BigDecimal.valueOf(35));

        // The versions of the updated account are both 0.
        assertEquals(0, accountForUserOne.getVersion().intValue());
        assertEquals(0, accountForUserTwo.getVersion().intValue());

        //Tries to saved both
        accountRepository.save(accountForUserOne);
        //Exception
        accountRepository.save(accountForUserTwo);
    }

    @After
    public void tearDown() {
        accountRepository.deleteAll();
    }
}