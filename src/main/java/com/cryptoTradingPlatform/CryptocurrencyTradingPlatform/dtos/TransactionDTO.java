package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private Long userId;
    //    private User user;
    private String cryptoSymbol;
    private TransactionType type;
    private BigDecimal quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalPrice;
    private BigDecimal profitOrLoss;
    private String timestamp;
}
