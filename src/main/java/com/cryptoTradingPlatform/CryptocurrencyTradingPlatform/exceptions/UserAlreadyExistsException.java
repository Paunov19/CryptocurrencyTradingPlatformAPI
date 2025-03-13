package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String email) {
        super("User with email " + email + "already exists");
    }
}
