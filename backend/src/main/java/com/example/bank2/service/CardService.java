package com.example.bank2.service;

import com.example.bank2.dto.CardCreateRequest;
import com.example.bank2.entity.Card;

import java.util.List;

public interface CardService {
    Card createCard(CardCreateRequest request, String userLogin);
    Card getCardByNumber(String cardNumber);
    List<Card> getUserCards(String userLogin);
    Card updateCardBalance(String cardNumber, java.math.BigDecimal newBalance);
    Card activateCard(String cardNumber);
    Card deactivateCard(String cardNumber);
    Card connectSbp(String cardNumber);
    Card disconnectSbp(String cardNumber);
}

