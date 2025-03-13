package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserCryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.UserCrypto;
import org.springframework.stereotype.Component;

@Component
public class UserCryptoMapper {

    public static UserCryptoDTO entityToDTO(UserCrypto userCrypto) {
        UserCryptoDTO userCryptoDTO = new UserCryptoDTO();
        userCryptoDTO.setUserId(userCrypto.getUser().getId());
        userCryptoDTO.setCryptoId(userCrypto.getCrypto().getId());
        userCryptoDTO.setQuantity(userCrypto.getQuantity());
        userCryptoDTO.setTotalAmount(userCrypto.getQuantity().multiply(userCrypto.getCrypto().getPrice()));
        return userCryptoDTO;
    }

    public static UserCrypto toEntity(UserCryptoDTO dto, User user, Crypto crypto) {
        UserCrypto userCrypto = new UserCrypto();
        userCrypto.setUser(user);
        userCrypto.setCrypto(crypto);
        userCrypto.setQuantity(dto.getQuantity());
        userCrypto.setTotalAmount(dto.getTotalAmount());
        return userCrypto;
    }
}

