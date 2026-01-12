package com.example.bank2.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "historytransfer")
public class HistoryTransfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sender_number_card", nullable = false)
    private String senderNumberCard;

    @Column(name = "recipient_number_card", nullable = false)
    private String recipientNumberCard;

    @Column(name = "transfer_amount", nullable = false)
    private BigDecimal transferAmount;

    @Column(nullable = false)
    private String date;

    // Constructors
    public HistoryTransfer() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSenderNumberCard() {
        return senderNumberCard;
    }

    public void setSenderNumberCard(String senderNumberCard) {
        this.senderNumberCard = senderNumberCard;
    }

    public String getRecipientNumberCard() {
        return recipientNumberCard;
    }

    public void setRecipientNumberCard(String recipientNumberCard) {
        this.recipientNumberCard = recipientNumberCard;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

