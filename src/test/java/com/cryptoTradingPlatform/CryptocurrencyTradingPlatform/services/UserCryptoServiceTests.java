package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.UserCrypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserCryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl.UserCryptoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class UserCryptoServiceTests {

    @Mock
    private UserService userService;

    @Mock
    private UserCryptoRepository userCryptoRepository;

    @Mock
    private CryptoRepository cryptoRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private UserCryptoServiceImpl userCryptoService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "user1", "user1@email.com", "password", BigDecimal.valueOf(1000), List.of());
    }


    @Test
    void testGetUserPortfolio() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        Crypto btc = new Crypto(1L, "BTC", BigDecimal.valueOf(50000));
        UserCrypto userCrypto = new UserCrypto(1L, user, btc, BigDecimal.valueOf(0.2), BigDecimal.valueOf(10000));

        when(userCryptoRepository.findByUser(user)).thenReturn(List.of(userCrypto));
        when(cryptoRepository.findById(btc.getId())).thenReturn(Optional.of(btc));

        var portfolio = userCryptoService.getUserPortfolio();

        assertEquals(1, portfolio.size());
        assertEquals(btc.getId(), portfolio.get(0).getCryptoId());

        assertEquals("BTC", btc.getSymbol());
    }



    @Test
    void testGetTotalPortfolioValue() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        Crypto btc = new Crypto(1L, "BTC", BigDecimal.valueOf(50));
        UserCrypto userCrypto = new UserCrypto(1L, user, btc, BigDecimal.valueOf(0.2), BigDecimal.valueOf(10000));

        when(userCryptoRepository.findByUser(user)).thenReturn(List.of(userCrypto));

        BigDecimal totalValue = userCryptoService.getTotalPortfolioValue();

        assertEquals(BigDecimal.valueOf(10000), totalValue);
    }

    @Test
    void testAddCryptoToPortfolio() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        Crypto btc = new Crypto(1L, "BTC", BigDecimal.valueOf(50));
        when(cryptoRepository.findBySymbol("BTC")).thenReturn(Optional.of(btc));

        userCryptoService.addCryptoToPortfolio(btc.getSymbol(), BigDecimal.valueOf(1));

        assertEquals(BigDecimal.valueOf(950), user.getBalance());
    }

    @Test
    void testRemoveCryptoFromPortfolio() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        Crypto btc = new Crypto(1L, "BTC", BigDecimal.valueOf(50));
        UserCrypto userCrypto = new UserCrypto(1L, user, btc, BigDecimal.valueOf(1), BigDecimal.valueOf(10000));

        when(cryptoRepository.findBySymbol("BTC")).thenReturn(Optional.of(btc));
        when(userCryptoRepository.findByUserAndCrypto(user, btc)).thenReturn(Optional.of(userCrypto));

        doNothing().when(transactionService).logSellTransaction("BTC", BigDecimal.valueOf(0.1), btc.getPrice(), BigDecimal.valueOf(5000));

        userCryptoService.removeCryptoFromPortfolio("BTC", BigDecimal.valueOf(0.1));

        assertEquals(BigDecimal.valueOf(1005.0), user.getBalance());
    }


}
