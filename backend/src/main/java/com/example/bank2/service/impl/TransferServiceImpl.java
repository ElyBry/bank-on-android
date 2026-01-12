package com.example.bank2.service.impl;

import com.example.bank2.dto.TransferRequest;
import com.example.bank2.entity.HistoryTransfer;
import com.example.bank2.repository.HistoryTransferRepository;
import com.example.bank2.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private HistoryTransferRepository historyTransferRepository;

    @Override
    public HistoryTransfer transfer(TransferRequest request) {
        // TODO: Implement transfer logic
        return null;
    }

    @Override
    public List<HistoryTransfer> getCardHistory(String cardNumber) {
        // TODO: Implement get card history
        return null;
    }

    @Override
    public List<HistoryTransfer> getUserHistory(String userLogin) {
        // TODO: Implement get user history
        return null;
    }
}

