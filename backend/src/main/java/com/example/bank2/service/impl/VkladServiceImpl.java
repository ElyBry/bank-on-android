package com.example.bank2.service.impl;

import com.example.bank2.dto.VkladCreateRequest;
import com.example.bank2.entity.Vklad;
import com.example.bank2.repository.VkladRepository;
import com.example.bank2.service.VkladService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkladServiceImpl implements VkladService {

    @Autowired
    private VkladRepository vkladRepository;

    @Override
    public Vklad createVklad(VkladCreateRequest request) {
        // TODO: Implement vklad creation logic
        return null;
    }

    @Override
    public Vklad getVkladByNumber(String numberVklad) {
        // TODO: Implement get vklad by number
        return null;
    }

    @Override
    public List<Vklad> getUserVklads(String userLogin) {
        // TODO: Implement get user vklads
        return null;
    }

    @Override
    public Vklad calculateVkladProfit(String numberVklad) {
        // TODO: Implement calculate vklad profit
        return null;
    }
}

