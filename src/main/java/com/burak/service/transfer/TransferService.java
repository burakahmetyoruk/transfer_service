package com.burak.service.transfer;

import com.burak.domain.entity.Account;
import com.burak.domain.repository.AccountRepository;
import com.burak.domain.service.TransferDomainService;
import com.burak.exception.AccountNotFoundException;
import com.burak.model.transfer.TransferRequest;
import com.burak.model.transfer.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Transfer Service that responsible for transferring money between accounts
 * @author byoruk
 */
@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferDomainService transferDomainService;
    private final AccountRepository accountRepository;

    /**
     * @param transferRequest TransferrerAccount Name , TransferredAccount Name, RequestedAmount
     * @return
     * @Throws AccountNotFoundException
     */
    public TransferResponse transferMoney(TransferRequest transferRequest) {
        //Transferrer Account
        Account transferrerAccount = getAccount(transferRequest.getTransferrerAccountName());
        //TransferredAccount
        Account transferredAccount = getAccount(transferRequest.getTransferredAccountName());
        //Transfer Money
        transferDomainService.transferMoney(transferrerAccount, transferredAccount, transferRequest.getTransferAmount());

        return new TransferResponse();
    }

    private Account getAccount(String accountName) {
        Account account = accountRepository.findByName(accountName);
        return Optional.ofNullable(account)
                .orElseThrow(() -> new AccountNotFoundException(accountName));
    }
}