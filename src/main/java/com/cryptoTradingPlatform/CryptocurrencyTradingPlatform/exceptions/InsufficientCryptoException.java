package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions;

public class InsufficientCryptoException extends RuntimeException {
    public InsufficientCryptoException(String message, String crypto) {
        super("User '" + message + "' has insufficient cryptocurrencie "+ crypto +" for this transaction.");
    }
}
