package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserCryptoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface UserCryptoService {
    List<UserCryptoDTO> getUserPortfolio();
    BigDecimal getTotalPortfolioValue();
    void addCryptoToPortfolio(String symbol, BigDecimal quantity);
    void removeCryptoFromPortfolio(String symbol, BigDecimal quantity);
}
