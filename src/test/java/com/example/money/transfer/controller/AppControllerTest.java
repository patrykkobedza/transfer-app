package com.example.money.transfer.controller;

import com.example.money.transfer.dto.AccountDTO;
import com.example.money.transfer.dto.TransferDTO;
import com.example.money.transfer.dto.TransferResultDTO;
import com.example.money.transfer.service.AccountService;
import com.example.money.transfer.service.TransferService;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AppControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AppController appController;
    @Mock
    private AccountService accountService;
    @Mock
    private TransferService transferService;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(appController).build();
    }

    @Test
    void transferMoneyTest() throws Exception {

        TransferDTO transferRequest = new TransferDTO(1L, 2L, BigDecimal.TEN);

        Mockito.when(transferService.transferMoney(any(TransferDTO.class)))
                .thenReturn(new TransferResultDTO("USD", "EUR", BigDecimal.valueOf(12.34)));

        mockMvc.perform(post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new JsonMapper().writeValueAsString(transferRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"sourceCurrency\":\"USD\",\"targetCurrency\":\"EUR\",\"exchangeRate\":12.34}"));
    }

    @Test
    void getAccountByOwnerTest() throws Exception {
        AccountDTO account = new AccountDTO(1L, "USD", BigDecimal.valueOf(123.32));

        Mockito.when(accountService.getAccount(account.getOwnerId())).thenReturn(account);

        mockMvc.perform(get("/account/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"ownerId\":1,\"currency\":\"USD\",\"balance\":123.32}"));
    }

    @Test
    void getAllAccountsTest() throws Exception {

        List<AccountDTO> accounts = Arrays.asList(
                new AccountDTO(1L, "USD", BigDecimal.valueOf(123.32)),
                new AccountDTO(2L, "PLN", BigDecimal.valueOf(542.22))
        );

        Mockito.when(accountService.getAllAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/account"))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"ownerId\":1,\"currency\":\"USD\",\"balance\":123.32},{\"ownerId\":2,\"currency\":\"PLN\",\"balance\":542.22}]"));
    }
}