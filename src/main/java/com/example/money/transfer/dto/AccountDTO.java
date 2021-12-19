package com.example.money.transfer.dto;

import com.example.money.transfer.model.Account;

import java.math.BigDecimal;

public class AccountDTO {

    private Long ownerId;
    private String currency;
    private BigDecimal balance;

    public AccountDTO(Long ownerId, String currency, BigDecimal balance) {
        this.ownerId = ownerId;
        this.currency = currency;
        this.balance = balance;
    }

    public AccountDTO(Account account) {
        this.ownerId = account.getOwnerId();
        this.currency = account.getCurrency();
        this.balance = account.getBalance();
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
