package com.example.money.transfer.service;

import com.example.money.transfer.dto.TransferDTO;
import com.example.money.transfer.dto.TransferResultDTO;

public interface TransferService {

    TransferResultDTO transferMoney(TransferDTO transferDTO);
}
