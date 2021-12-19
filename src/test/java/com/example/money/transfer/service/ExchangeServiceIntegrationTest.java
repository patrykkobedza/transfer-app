package com.example.money.transfer.service;

import com.example.money.transfer.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class ExchangeServiceIntegrationTest {

    @Autowired
    private ExchangeService exchangeService;

    @Test
    public void getExchangeRateShouldReturnProperExchangeRateForDifferentCurrencies() {
        BigDecimal result = exchangeService.getExchangeRate("PLN", "USD");
        assertThat(result.doubleValue(), is(greaterThan(0d)));
    }

    @Test
    public void getExchangeRateShouldReturnProperExchangeRateForSameCurrencies() {
        BigDecimal result = exchangeService.getExchangeRate("USD", "USD");
        assertThat(result.doubleValue(), is(equalTo(1d)));
    }

    @Test
    public void getExchangeRateShouldThrowOnInvalidSourceCurrency() {
        Assertions.assertThrows(NotFoundException.class, () -> exchangeService.getExchangeRate("NOTEXISTINGCURRENCY", "EUR"));
    }

    @Test
    public void getExchangeRateShouldThrowOnInvalidTargetSourceCurrency() {
        Assertions.assertThrows(NotFoundException.class, () -> exchangeService.getExchangeRate("USD", "NOTEXISTINGCURRENCY"));
    }

    @Test
    public void getExchangeRateShouldThrowOnInvalidBothCurrencies() {
        Assertions.assertThrows(NotFoundException.class, () -> exchangeService.getExchangeRate("NOTEXISTINGCURRENCY1", "NOTEXISTINGCURRENCY2"));

    }


}