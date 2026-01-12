package com.example.bank2.repository;

import com.example.bank2.entity.Zayavka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZayavkaRepository extends JpaRepository<Zayavka, Integer> {
    List<Zayavka> findByLoginUser(String loginUser);
    List<Zayavka> findByIsSuccessfulFalse();
    List<Zayavka> findByIsSuccessfulTrue();
}

