package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Transaction;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.TransactionType;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    Optional<Transaction> findByUserAndCryptoSymbolAndType(User user, String cryptocurrency, TransactionType type);

}
