package com.example.bank2.service;

import com.example.bank2.entity.InterestRate;

import java.util.List;

public interface InterestRateService {
    List<InterestRate> getAllInterestRates();
    InterestRate getInterestRateByTerm(String term);
    InterestRate createOrUpdateInterestRate(String term, Double interest);
}

