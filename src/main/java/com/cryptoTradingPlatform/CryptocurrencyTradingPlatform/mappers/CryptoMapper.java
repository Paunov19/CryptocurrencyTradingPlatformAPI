package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.CryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import org.springframework.stereotype.Component;

@Component
public class CryptoMapper {

    public static CryptoDTO entityToDTO(Crypto crypto) {
        CryptoDTO cryptoDTO = new CryptoDTO();
        cryptoDTO.setSymbol(crypto.getSymbol());
        cryptoDTO.setPrice(crypto.getPrice());
        return cryptoDTO;
    }

    public static Crypto toEntity(CryptoDTO cryptoDTO) {
        Crypto crypto = new Crypto();
        crypto.setSymbol(cryptoDTO.getSymbol());
        crypto.setPrice(cryptoDTO.getPrice());
        return crypto;
    }
}
