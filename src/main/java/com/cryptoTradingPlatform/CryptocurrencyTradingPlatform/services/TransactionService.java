package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getUserTransactions();
    void logBuyTransaction(String symbol, BigDecimal quantity, BigDecimal price, BigDecimal totalAmount);
    void logSellTransaction(String symbol, BigDecimal quantity, BigDecimal sellPrice, BigDecimal totalSalePrice);
}

