package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.UserAlreadyExistsException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers.UserMapper;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload.UserRequest;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserCryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.security.services.UserDetailsImpl;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCryptoRepository userCryptoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final BigDecimal INITIAL_BALANCE = BigDecimal.valueOf(10000);

    @Override
    @Transactional
    public UserDTO register(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new UserAlreadyExistsException(userRequest.email());
        }
        User user = new User();
        user.setUsername(userRequest.username());
        user.setEmail(userRequest.email());
        user.setPassword(passwordEncoder.encode(userRequest.password()));
        user.setBalance(INITIAL_BALANCE);
        user.setPortfolio(new ArrayList<>());
        userRepository.save(user);

        return UserMapper.entityToDTO(user);
    }

    @Override
    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("No user currently authenticated");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof UserDetailsImpl)) {
            throw new SecurityException("Current principal is not a UserDetailsImpl");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) principal;
        String email = userDetails.getUsername();
        return userRepository.findByEmail(email).orElseThrow(() -> new SecurityException("Authenticated user not found in database"));
    }

    @Override
    public void resetAccount() {
        User user = getCurrentAuthenticatedUser();
        userCryptoRepository.deleteByUser(user);
        user.setBalance(INITIAL_BALANCE);
        userRepository.save(user);
    }
}
