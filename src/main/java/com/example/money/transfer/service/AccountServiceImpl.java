package com.example.money.transfer.service;

import com.example.money.transfer.dto.AccountDTO;
import com.example.money.transfer.exception.NotFoundException;
import com.example.money.transfer.model.Account;
import com.example.money.transfer.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO getAccount(long ownerId) {
        Account account = accountRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NotFoundException("Account not found for ownerId: " + ownerId));
        return new AccountDTO(account);
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList());
    }
}
