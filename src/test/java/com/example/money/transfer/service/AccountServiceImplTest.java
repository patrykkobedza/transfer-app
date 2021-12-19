package com.example.money.transfer.service;

import com.example.money.transfer.dto.AccountDTO;
import com.example.money.transfer.exception.NotFoundException;
import com.example.money.transfer.model.Account;
import com.example.money.transfer.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    private AccountServiceImpl accountService;

    @Mock
    private AccountRepository accountRepositoryMock;

    @Test
    void getAccountThatExistTest() {
        Account account = new Account(1l, "USD", BigDecimal.TEN);
        Mockito.when(accountRepositoryMock.findByOwnerId(1L)).thenReturn(Optional.of(account));

        AccountDTO result = accountService.getAccount(1L);

        assertThat(result,
                allOf(
                        hasProperty("ownerId", is(1L)),
                        hasProperty("currency", is("USD")),
                        hasProperty("balance", is(BigDecimal.valueOf(10)))
                )
        );
    }

    @Test
    void getAccountThatNotExistShouldThrowTest() {
        Mockito.when(accountRepositoryMock.findByOwnerId(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.getAccount(1L));
    }

    @Test
    void getAllAccountsTest() {

        List<Account> accounts = Arrays.asList(
                new Account(1L, "USD", BigDecimal.ONE),
                new Account(2L, "EUR", BigDecimal.TEN)
        );
        Mockito.when(accountRepositoryMock.findAll()).thenReturn(accounts);

        List<AccountDTO> result = accountService.getAllAccounts();

        assertThat(result, contains(
                allOf(
                        hasProperty("ownerId", is(1L)),
                        hasProperty("currency", is("USD")),
                        hasProperty("balance", is(BigDecimal.valueOf(1)))),
                allOf(
                        hasProperty("ownerId", is(2L)),
                        hasProperty("currency", is("EUR")),
                        hasProperty("balance", is(BigDecimal.valueOf(10))))
                )
        );
    }
}