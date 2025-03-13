package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.CryptoDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CryptoService {
    List<CryptoDTO> getAllCryptos();
    void updateCryptoPrice(String symbol, BigDecimal newPrice);
}
