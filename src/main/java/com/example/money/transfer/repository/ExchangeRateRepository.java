package com.example.money.transfer.repository;

import com.example.money.transfer.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findBySourceCodeAndTargetCode(String sourceCode, String targetCode);
}
