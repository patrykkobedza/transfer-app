package com.example.money.transfer.dto;

import java.math.BigDecimal;

public class TransferResultDTO {

    private final String sourceCurrency;
    private final String targetCurrency;
    private final BigDecimal exchangeRate;

    public TransferResultDTO(String sourceCurrency, String targetCurrency, BigDecimal exchangeRate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRate = exchangeRate;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
