package com.example.money.transfer.service;

import com.example.money.transfer.dto.TransferDTO;
import com.example.money.transfer.dto.TransferResultDTO;
import com.example.money.transfer.exception.InsufficientBalanceException;
import com.example.money.transfer.exception.NotFoundException;
import com.example.money.transfer.model.Account;
import com.example.money.transfer.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Transactional
@Service
public class TransferServiceImpl implements TransferService {

    Logger logger = LoggerFactory.getLogger(ExchangeServiceImpl.class);

    private final AccountRepository accountRepository;
    private final ExchangeService exchangeService;

    public TransferServiceImpl(AccountRepository accountRepository, ExchangeService exchangeService) {
        this.accountRepository = accountRepository;
        this.exchangeService = exchangeService;
    }

    @Override
    public TransferResultDTO transferMoney(TransferDTO transferDTO) {
        Account sourceAccount = accountRepository.findByOwnerId(transferDTO.getSenderId())
                .orElseThrow(() -> new NotFoundException("Source account not found."));
        Account targetAccount = accountRepository.findByOwnerId(transferDTO.getReceiverId())
                .orElseThrow(() -> new NotFoundException("Target account not found."));

        if (!isEnoughBalance(transferDTO, sourceAccount)) {
            throw new InsufficientBalanceException("Insufficient founds on source accountId:" + sourceAccount.getOwnerId());
        }
        BigDecimal exchangeRate = exchangeService.getExchangeRate(sourceAccount.getCurrency(), targetAccount.getCurrency());
        subtractTransferedAmountFromSourceAccount(transferDTO, sourceAccount);
        BigDecimal transferredAmountAfterExchange = transferDTO.getAmount().multiply(exchangeRate).setScale(2, RoundingMode.HALF_EVEN);
        addTransferedAmountToTargetAccount(targetAccount, transferredAmountAfterExchange);

        logger.info(
                "TRANSFER: " + transferDTO.getAmount() + sourceAccount.getCurrency() +
                        " from accountId:" + sourceAccount.getOwnerId() +
                        " to accountId:" + targetAccount.getOwnerId() +
                        " as " + transferredAmountAfterExchange + targetAccount.getCurrency() +
                        " with exchangeRate:" + exchangeRate);
        logger.info("NEW BALANCE: accountId:" + sourceAccount.getOwnerId() + " " + sourceAccount.getBalance() + sourceAccount.getCurrency() +
                ", accountId:" + targetAccount.getOwnerId() + " " + targetAccount.getBalance() + targetAccount.getCurrency());

        return new TransferResultDTO(sourceAccount.getCurrency(), targetAccount.getCurrency(), exchangeRate);
    }

    private void addTransferedAmountToTargetAccount(Account targetAccount, BigDecimal transferredAmountAfterExchange) {
        targetAccount.setBalance(targetAccount.getBalance().add(transferredAmountAfterExchange));
    }

    private void subtractTransferedAmountFromSourceAccount(TransferDTO transferDTO, Account sourceAccount) {
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferDTO.getAmount()));
    }

    private boolean isEnoughBalance(TransferDTO transferDTO, Account sourceAccount) {
        return sourceAccount.getBalance().compareTo(transferDTO.getAmount()) >= 0;
    }
}
