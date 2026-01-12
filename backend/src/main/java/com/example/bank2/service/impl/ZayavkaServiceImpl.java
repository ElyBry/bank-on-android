package com.example.bank2.service.impl;

import com.example.bank2.dto.ZayavkaCreateRequest;
import com.example.bank2.entity.Zayavka;
import com.example.bank2.repository.ZayavkaRepository;
import com.example.bank2.service.ZayavkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZayavkaServiceImpl implements ZayavkaService {

    @Autowired
    private ZayavkaRepository zayavkaRepository;

    @Override
    public Zayavka createZayavka(ZayavkaCreateRequest request) {
        // TODO: Implement zayavka creation logic
        return null;
    }

    @Override
    public List<Zayavka> getUserZayavki(String userLogin) {
        // TODO: Implement get user zayavki
        return null;
    }

    @Override
    public List<Zayavka> getAllPendingZayavki() {
        // TODO: Implement get all pending zayavki
        return null;
    }

    @Override
    public List<Zayavka> getAllProcessedZayavki() {
        // TODO: Implement get all processed zayavki
        return null;
    }

    @Override
    public Zayavka processZayavka(Integer zayavkaId, boolean isSuccessful) {
        // TODO: Implement process zayavka
        return null;
    }
}

