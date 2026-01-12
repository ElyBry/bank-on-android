package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.entity.InterestRate;
import com.example.bank2.service.InterestRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interest-rates")
public class InterestRateController {

    @Autowired
    private InterestRateService interestRateService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InterestRate>>> getAllInterestRates() {
        // TODO: Implement get all interest rates endpoint
        return null;
    }

    @GetMapping("/{term}")
    public ResponseEntity<ApiResponse<InterestRate>> getInterestRateByTerm(@PathVariable String term) {
        // TODO: Implement get interest rate by term endpoint
        return null;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InterestRate>> createOrUpdateInterestRate(@RequestParam String term, @RequestParam Double interest) {
        // TODO: Implement create or update interest rate endpoint
        return null;
    }
}

