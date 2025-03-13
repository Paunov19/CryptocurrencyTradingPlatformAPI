package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.UserCrypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCryptoRepository extends JpaRepository<UserCrypto, Long> {
    List<UserCrypto> findByUser(User user);
    Optional<UserCrypto> findByUserAndCrypto(User user, Crypto crypto);
    void deleteByUser(User user);
}
