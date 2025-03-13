package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload;

import java.math.BigDecimal;

public record CryptoRequest(String symbol, BigDecimal quantity) {
}
