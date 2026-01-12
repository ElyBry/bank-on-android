package com.example.bank2.service.impl;

import com.example.bank2.entity.InterestRate;
import com.example.bank2.repository.InterestRateRepository;
import com.example.bank2.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestRateServiceImpl implements InterestRateService {

    @Autowired
    private InterestRateRepository interestRateRepository;

    @Override
    public List<InterestRate> getAllInterestRates() {
        // TODO: Implement get all interest rates
        return null;
    }

    @Override
    public InterestRate getInterestRateByTerm(String term) {
        // TODO: Implement get interest rate by term
        return null;
    }

    @Override
    public InterestRate createOrUpdateInterestRate(String term, Double interest) {
        // TODO: Implement create or update interest rate
        return null;
    }
}

