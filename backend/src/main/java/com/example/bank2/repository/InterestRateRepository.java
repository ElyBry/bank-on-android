package com.example.bank2.repository;

import com.example.bank2.entity.InterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRateRepository extends JpaRepository<InterestRate, Integer> {
    Optional<InterestRate> findByTerm(String term);
}

