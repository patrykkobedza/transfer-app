package com.example.money.transfer.service;

import java.math.BigDecimal;

public interface ExchangeService {

    BigDecimal getExchangeRate(String sourceCurrency, String targetCurrency);
}
