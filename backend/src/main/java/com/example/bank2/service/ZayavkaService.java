package com.example.bank2.service;

import com.example.bank2.dto.ZayavkaCreateRequest;
import com.example.bank2.entity.Zayavka;

import java.util.List;

public interface ZayavkaService {
    Zayavka createZayavka(ZayavkaCreateRequest request);
    List<Zayavka> getUserZayavki(String userLogin);
    List<Zayavka> getAllPendingZayavki();
    List<Zayavka> getAllProcessedZayavki();
    Zayavka processZayavka(Integer zayavkaId, boolean isSuccessful);
}

