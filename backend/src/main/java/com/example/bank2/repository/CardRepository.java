package com.example.bank2.repository;

import com.example.bank2.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByCardNumber(String cardNumber);
    List<Card> findByHolderName(String holderName);
    List<Card> findByHolderNameAndIsActiveTrue(String holderName);
    boolean existsByCardNumber(String cardNumber);
}

