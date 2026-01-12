package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.dto.CardCreateRequest;
import com.example.bank2.entity.Card;
import com.example.bank2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @PostMapping
    public ResponseEntity<ApiResponse<Card>> createCard(@RequestBody CardCreateRequest request, @RequestParam String userLogin) {
        // TODO: Implement create card endpoint
        return null;
    }

    @GetMapping("/{cardNumber}")
    public ResponseEntity<ApiResponse<Card>> getCardByNumber(@PathVariable String cardNumber) {
        // TODO: Implement get card by number endpoint
        return null;
    }

    @GetMapping("/user/{userLogin}")
    public ResponseEntity<ApiResponse<List<Card>>> getUserCards(@PathVariable String userLogin) {
        // TODO: Implement get user cards endpoint
        return null;
    }

    @PutMapping("/{cardNumber}/activate")
    public ResponseEntity<ApiResponse<Card>> activateCard(@PathVariable String cardNumber) {
        // TODO: Implement activate card endpoint
        return null;
    }

    @PutMapping("/{cardNumber}/deactivate")
    public ResponseEntity<ApiResponse<Card>> deactivateCard(@PathVariable String cardNumber) {
        // TODO: Implement deactivate card endpoint
        return null;
    }

    @PutMapping("/{cardNumber}/sbp/connect")
    public ResponseEntity<ApiResponse<Card>> connectSbp(@PathVariable String cardNumber) {
        // TODO: Implement connect SBP endpoint
        return null;
    }

    @PutMapping("/{cardNumber}/sbp/disconnect")
    public ResponseEntity<ApiResponse<Card>> disconnectSbp(@PathVariable String cardNumber) {
        // TODO: Implement disconnect SBP endpoint
        return null;
    }
}

