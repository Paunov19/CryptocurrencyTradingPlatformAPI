package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String name;
    private String email;
    private BigDecimal balance;
    private List<UserCryptoDTO> portfolio;
//    private List<UserCrypto> portfolio;
}
