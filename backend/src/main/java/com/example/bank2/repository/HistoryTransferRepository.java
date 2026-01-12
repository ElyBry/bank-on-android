package com.example.bank2.repository;

import com.example.bank2.entity.HistoryTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryTransferRepository extends JpaRepository<HistoryTransfer, Integer> {
    List<HistoryTransfer> findBySenderNumberCard(String senderNumberCard);
    List<HistoryTransfer> findByRecipientNumberCard(String recipientNumberCard);
    List<HistoryTransfer> findBySenderNumberCardOrRecipientNumberCard(String senderNumberCard, String recipientNumberCard);
}

