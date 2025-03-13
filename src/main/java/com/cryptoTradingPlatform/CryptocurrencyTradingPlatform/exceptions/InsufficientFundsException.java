package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super("User '" + message + "' has insufficient funds for this transaction.");
    }
}
