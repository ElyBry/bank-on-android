package com.example.bank2.repository;

import com.example.bank2.entity.Vklad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VkladRepository extends JpaRepository<Vklad, Integer> {
    Optional<Vklad> findByNumberVklad(String numberVklad);
    List<Vklad> findByUserLogin(String userLogin);
    boolean existsByNumberVklad(String numberVklad);
}

