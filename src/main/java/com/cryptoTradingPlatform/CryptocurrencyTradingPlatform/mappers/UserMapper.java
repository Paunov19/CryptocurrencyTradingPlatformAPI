package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserCryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.UserCrypto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public static UserDTO entityToDTO(User user) {
        List<UserCryptoDTO> portfolio = user.getPortfolio().stream().map(UserCryptoMapper::entityToDTO).toList();

        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setBalance(user.getBalance());
        userDTO.setPortfolio(portfolio);
        return userDTO;
    }

    public static User toEntity(UserDTO dto, List<UserCrypto> portfolio) {
        User user = new User();
        user.setUsername (dto.getName());
        user.setEmail(dto.getEmail());
        user.setBalance(dto.getBalance());
        user.setPortfolio(portfolio);
        return user;
    }
}
