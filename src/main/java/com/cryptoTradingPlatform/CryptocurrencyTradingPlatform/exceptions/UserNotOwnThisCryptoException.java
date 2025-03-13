package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions;

public class UserNotOwnThisCryptoException extends RuntimeException {
    public UserNotOwnThisCryptoException(String message, String cryptoSymbol) {
        super("User " + message + " does not own this crypto " + cryptoSymbol);
    }
}
