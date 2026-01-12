package com.example.bank2.service.impl;

import com.example.bank2.dto.CardCreateRequest;
import com.example.bank2.entity.Card;
import com.example.bank2.repository.CardRepository;
import com.example.bank2.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Card createCard(CardCreateRequest request, String userLogin) {
        // TODO: Implement card creation logic
        return null;
    }

    @Override
    public Card getCardByNumber(String cardNumber) {
        // TODO: Implement get card by number
        return null;
    }

    @Override
    public List<Card> getUserCards(String userLogin) {
        // TODO: Implement get user cards
        return null;
    }

    @Override
    public Card updateCardBalance(String cardNumber, java.math.BigDecimal newBalance) {
        // TODO: Implement update card balance
        return null;
    }

    @Override
    public Card activateCard(String cardNumber) {
        // TODO: Implement activate card
        return null;
    }

    @Override
    public Card deactivateCard(String cardNumber) {
        // TODO: Implement deactivate card
        return null;
    }

    @Override
    public Card connectSbp(String cardNumber) {
        // TODO: Implement connect SBP
        return null;
    }

    @Override
    public Card disconnectSbp(String cardNumber) {
        // TODO: Implement disconnect SBP
        return null;
    }
}

