package com.example.money.transfer.service;

import com.example.money.transfer.dto.TransferDTO;
import com.example.money.transfer.dto.TransferResultDTO;
import com.example.money.transfer.exception.InsufficientBalanceException;
import com.example.money.transfer.exception.NotFoundException;
import com.example.money.transfer.model.Account;
import com.example.money.transfer.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @InjectMocks
    private TransferServiceImpl transferService;

    @Mock
    private AccountRepository accountRepositoryMock;
    @Mock
    private ExchangeService exchangeService;

    @Test
    public void transferMoneyShouldUpdateBalances() {
        Account sourceAccount = new Account(1L, "USD", BigDecimal.valueOf(5));
        Account targetAccount = new Account(2L, "EUR", BigDecimal.valueOf(15));

        BigDecimal usdEurExchangeRate = BigDecimal.valueOf(0.81);

        Mockito.when(accountRepositoryMock.findByOwnerId(1L)).thenReturn(Optional.of(sourceAccount));
        Mockito.when(accountRepositoryMock.findByOwnerId(2L)).thenReturn(Optional.of(targetAccount));
        Mockito.when(exchangeService.getExchangeRate("USD", "EUR")).thenReturn(usdEurExchangeRate);

        TransferResultDTO result = transferService.transferMoney(new TransferDTO(sourceAccount.getOwnerId(), targetAccount.getOwnerId(), BigDecimal.valueOf(2.51)));

        assertThat(result, allOf(
                hasProperty("sourceCurrency", is(sourceAccount.getCurrency())),
                hasProperty("targetCurrency", is(targetAccount.getCurrency())),
                hasProperty("exchangeRate", is(usdEurExchangeRate))
        ));

        assertThat(sourceAccount, hasProperty("balance", is(BigDecimal.valueOf(2.49))));
        assertThat(targetAccount, hasProperty("balance", is(BigDecimal.valueOf(17.03))));
    }


    @Test
    public void transferMoneyShouldThrowOnNotExistingSourceAccount() {

        Mockito.when(accountRepositoryMock.findByOwnerId(1L)).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> transferService.transferMoney(new TransferDTO(1L, 2L, BigDecimal.valueOf(3)))
        );

        assertThat(thrown.getMessage(), containsString("Source account not found."));
    }

    @Test
    public void transferMoneyShouldThrowOnNotExistingTargetAccount() {
        Account sourceAccount = new Account(1L, "USD", BigDecimal.valueOf(5));
        Mockito.when(accountRepositoryMock.findByOwnerId(1L)).thenReturn(Optional.of(sourceAccount));
        Mockito.when(accountRepositoryMock.findByOwnerId(2L)).thenReturn(Optional.empty());

        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> transferService.transferMoney(new TransferDTO(1L, 2L, BigDecimal.valueOf(3)))
        );

        assertThat(thrown.getMessage(), containsString("Target account not found."));
    }

    @Test
    public void transferMoneyShouldThrowOnNotSufficientBalance() {
        Account sourceAccount = new Account(1L, "USD", BigDecimal.valueOf(5));
        Account targetAccount = new Account(2L, "EUR", BigDecimal.valueOf(15));
        Mockito.when(accountRepositoryMock.findByOwnerId(1L)).thenReturn(Optional.of(sourceAccount));
        Mockito.when(accountRepositoryMock.findByOwnerId(2L)).thenReturn(Optional.of(targetAccount));

        InsufficientBalanceException thrown = assertThrows(
                InsufficientBalanceException.class,
                () -> transferService.transferMoney(new TransferDTO(1L, 2L, BigDecimal.valueOf(60)))
        );

        assertThat(thrown.getMessage(), containsString("Insufficient founds on source accountId:1"));
    }

}