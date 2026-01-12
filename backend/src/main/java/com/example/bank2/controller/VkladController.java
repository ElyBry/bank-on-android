package com.example.bank2.controller;

import com.example.bank2.dto.ApiResponse;
import com.example.bank2.dto.VkladCreateRequest;
import com.example.bank2.entity.Vklad;
import com.example.bank2.service.VkladService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vklads")
public class VkladController {

    @Autowired
    private VkladService vkladService;

    @PostMapping
    public ResponseEntity<ApiResponse<Vklad>> createVklad(@RequestBody VkladCreateRequest request) {
        // TODO: Implement create vklad endpoint
        return null;
    }

    @GetMapping("/{numberVklad}")
    public ResponseEntity<ApiResponse<Vklad>> getVkladByNumber(@PathVariable String numberVklad) {
        // TODO: Implement get vklad by number endpoint
        return null;
    }

    @GetMapping("/user/{userLogin}")
    public ResponseEntity<ApiResponse<List<Vklad>>> getUserVklads(@PathVariable String userLogin) {
        // TODO: Implement get user vklads endpoint
        return null;
    }

    @GetMapping("/{numberVklad}/profit")
    public ResponseEntity<ApiResponse<Vklad>> calculateProfit(@PathVariable String numberVklad) {
        // TODO: Implement calculate profit endpoint
        return null;
    }
}

