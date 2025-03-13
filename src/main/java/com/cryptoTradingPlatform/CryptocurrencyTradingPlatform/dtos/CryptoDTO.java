package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoDTO {

    private String symbol;
    private BigDecimal price;
}
