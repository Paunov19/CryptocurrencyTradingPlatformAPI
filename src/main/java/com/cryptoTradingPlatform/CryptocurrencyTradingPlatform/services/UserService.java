package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload.UserRequest;

public interface UserService {
    UserDTO register(UserRequest userRequest);
    User getCurrentAuthenticatedUser();
    void resetAccount();
}
