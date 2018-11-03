package com.burak.service;

import com.burak.domain.entity.Account;
import com.burak.domain.entity.Transfer;
import com.burak.domain.repository.TransferRepository;
import com.burak.exception.AccountNotFoundException;
import com.burak.exception.NegativeBalanceException;
import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Transfer Service that responsible for transferring money between accounts
 * @author byoruk
 */
@Service
@RequiredArgsConstructor
public class TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private final AccountService accountService;
    private final TransferRepository transferRepository;
    private final LockService lockService;

    /**
     * @param transferRequest TransferrerAccount Name , TransferredAccount Name, RequestedAmount
     * @throws AccountNotFoundException if transferrer account or transferred account not exist
     */
    public TransferResponse transfer(TransferRequest transferRequest) {
        try {
            lockService.lock();
            //Retrieve Transferrer Account
            Account transferrerAccount = accountService.retrieveAccount(transferRequest.getTransferrerAccountName());
            //Retrieve TransferredAccount
            Account transferredAccount = accountService.retrieveAccount(transferRequest.getTransferredAccountName());
            //Transfer Money
            TransferResponse transferResponse = saveTransfer(transferrerAccount,
                    transferredAccount,
                    transferRequest.getTransferAmount());
            logger.info("Transfer Successful From: {} To: {}", transferrerAccount.getName(), transferredAccount.getName());
            return transferResponse;
        } finally {
            lockService.unlock();
        }
    }

    /**
     * @param transferrerAccount Transferrer Account
     * @param transferredAccount Transferred Account
     * @param transferAmount     Transfer Amount
     * @throws NegativeBalanceException if transferrer account balance less than transfer amount
     */
    @Transactional(rollbackFor = Exception.class, noRollbackFor = NegativeBalanceException.class)
    public TransferResponse saveTransfer(Account transferrerAccount, Account transferredAccount, final BigDecimal transferAmount) {
        //Account must not be overdrawn
        if (transferrerAccount.getBalance().compareTo(transferAmount) < 0) {
            logger.info("{} Balance must be bigger than Transfer Amount", transferrerAccount.getName());
            throw new NegativeBalanceException(transferrerAccount.getName());
        }
        logger.info("Transfer Amount:{}, From: {}, To: {}",
                transferAmount,
                transferrerAccount.getName(),
                transferredAccount.getName());

        transferrerAccount.setBalance(transferrerAccount.getBalance().subtract(transferAmount));
        accountService.save(transferrerAccount);

        transferredAccount.setBalance(transferredAccount.getBalance().add(transferAmount));
        accountService.save(transferredAccount);

        Transfer transfer = new Transfer();
        transfer.setTransferAmount(transferAmount);
        transfer.setTransferrer(transferrerAccount);
        transfer.setTransferred(transferredAccount);
        transferRepository.save(transfer);

        return new TransferResponse(transfer.getId(),
                System.nanoTime(),
                transferAmount,
                transferrerAccount.getName(),
                transferredAccount.getName());
    }
}