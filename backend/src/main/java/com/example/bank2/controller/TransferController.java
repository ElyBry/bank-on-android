package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.dto.TransferRequest;
import com.example.bank2.entity.HistoryTransfer;
import com.example.bank2.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @PostMapping
    public ResponseEntity<ApiResponse<HistoryTransfer>> transfer(@RequestBody TransferRequest request) {
        // TODO: Implement transfer endpoint
        return null;
    }

    @GetMapping("/card/{cardNumber}")
    public ResponseEntity<ApiResponse<List<HistoryTransfer>>> getCardHistory(@PathVariable String cardNumber) {
        // TODO: Implement get card history endpoint
        return null;
    }

    @GetMapping("/user/{userLogin}")
    public ResponseEntity<ApiResponse<List<HistoryTransfer>>> getUserHistory(@PathVariable String userLogin) {
        // TODO: Implement get user history endpoint
        return null;
    }
}

