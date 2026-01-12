package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.dto.ZayavkaCreateRequest;
import com.example.bank2.entity.Zayavka;
import com.example.bank2.service.ZayavkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zayavki")
public class ZayavkaController {

    @Autowired
    private ZayavkaService zayavkaService;

    @PostMapping
    public ResponseEntity<ApiResponse<Zayavka>> createZayavka(@RequestBody ZayavkaCreateRequest request) {
        // TODO: Implement create zayavka endpoint
        return null;
    }

    @GetMapping("/user/{userLogin}")
    public ResponseEntity<ApiResponse<List<Zayavka>>> getUserZayavki(@PathVariable String userLogin) {
        // TODO: Implement get user zayavki endpoint
        return null;
    }

    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Zayavka>>> getPendingZayavki() {
        // TODO: Implement get pending zayavki endpoint
        return null;
    }

    @GetMapping("/processed")
    public ResponseEntity<ApiResponse<List<Zayavka>>> getProcessedZayavki() {
        // TODO: Implement get processed zayavki endpoint
        return null;
    }

    @PutMapping("/{zayavkaId}/process")
    public ResponseEntity<ApiResponse<Zayavka>> processZayavka(@PathVariable Integer zayavkaId, @RequestParam boolean isSuccessful) {
        // TODO: Implement process zayavka endpoint
        return null;
    }
}

