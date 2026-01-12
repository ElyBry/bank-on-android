package com.example.bank2.service;

import com.example.bank2.dto.VkladCreateRequest;
import com.example.bank2.entity.Vklad;

import java.util.List;

public interface VkladService {
    Vklad createVklad(VkladCreateRequest request);
    Vklad getVkladByNumber(String numberVklad);
    List<Vklad> getUserVklads(String userLogin);
    Vklad calculateVkladProfit(String numberVklad);
}

