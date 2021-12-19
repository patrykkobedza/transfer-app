package com.example.money.transfer.service;

import com.example.money.transfer.dto.AccountDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface AccountService {

    AccountDTO getAccount(long ownerId) throws EntityNotFoundException;

    List<AccountDTO> getAllAccounts();
}
