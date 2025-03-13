package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseServiceException extends RuntimeException {

    private final int statusCode;

    public BaseServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
