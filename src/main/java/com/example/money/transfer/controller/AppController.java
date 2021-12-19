package com.example.money.transfer.controller;


import com.example.money.transfer.dto.AccountDTO;
import com.example.money.transfer.dto.TransferDTO;
import com.example.money.transfer.dto.TransferResultDTO;
import com.example.money.transfer.service.AccountService;
import com.example.money.transfer.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
public class AppController {

    private final AccountService accountService;
    private final TransferService transferService;

    public AppController(AccountService accountService, TransferService transferService) {
        this.accountService = accountService;
        this.transferService = transferService;
    }

    @Operation(summary = "Transfer money from one account to another and exchange money if target account has different currency")
    @PostMapping("/transfer")
    public TransferResultDTO transferMoney(@Valid @RequestBody TransferDTO transferDTO) {
        return transferService.transferMoney(transferDTO);
    }

    @Operation(summary = "Return one account details basing on owner Id")
    @GetMapping("/account/{id}")
    public AccountDTO getAccountByOwner(@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    @Operation(summary = "Get all accounts info, for checking transfer results")
    @GetMapping("/account")
    public List<AccountDTO> getAllAccounts() {
        return accountService.getAllAccounts();
    }

}
