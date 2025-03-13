package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long> {
    Optional<Crypto> findBySymbol(String symbol);
}
