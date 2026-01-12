package com.example.bank2.service;

import com.example.bank2.dto.TransferRequest;
import com.example.bank2.entity.HistoryTransfer;

import java.util.List;

public interface TransferService {
    HistoryTransfer transfer(TransferRequest request);
    List<HistoryTransfer> getCardHistory(String cardNumber);
    List<HistoryTransfer> getUserHistory(String userLogin);
}

