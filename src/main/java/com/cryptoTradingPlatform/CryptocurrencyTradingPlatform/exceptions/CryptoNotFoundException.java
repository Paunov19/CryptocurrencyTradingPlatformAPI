package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions;

public class CryptoNotFoundException extends RuntimeException {
    public CryptoNotFoundException(String message) {
        super("Cryptocurrency with symbol '" + message + "' not found.");
    }
}
