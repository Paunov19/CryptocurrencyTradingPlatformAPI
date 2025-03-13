package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload.UserRequest;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserCryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserServiceTests {

        @Mock
        private UserRepository userRepository;

        @Mock
        private UserCryptoRepository userCryptoRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @InjectMocks
        private UserServiceImpl userService;

        private User user;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            user = new User(1L, "user1", "user1@email.com", "encodedPassword", BigDecimal.valueOf(10000), null);
        }

        @Test
        void testRegisterUser() {
            UserRequest userRequest = new UserRequest("user1@email.com", "user1", "password");

            when(userRepository.existsByEmail(userRequest.email())).thenReturn(false);
            when(passwordEncoder.encode(userRequest.password())).thenReturn("encodedPassword");
            UserDTO result = userService.register(userRequest);

            assertEquals("user1", result.getName());
            assertEquals("user1@email.com", result.getEmail());
            assertEquals(BigDecimal.valueOf(10000), result.getBalance());
        }

    }
