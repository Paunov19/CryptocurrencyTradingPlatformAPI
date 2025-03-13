package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Transaction;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.TransactionType;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.TransactionRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransactionServiceTests {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private UserService userService;

    @Mock
    private CryptoRepository cryptoRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "user1", "user1@email.com", "password", BigDecimal.valueOf(1000), List.of());

    }

    @Test
    void testGetAllTransactions() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        Transaction transaction1 = new Transaction(1L, user, "BTC", TransactionType.BUY, BigDecimal.valueOf(1), BigDecimal.valueOf(50000), BigDecimal.valueOf(50000), BigDecimal.ZERO, LocalDateTime.now());
        Transaction transaction2 = new Transaction(2L, user, "ETH", TransactionType.SELL, BigDecimal.valueOf(2), BigDecimal.valueOf(3000), BigDecimal.valueOf(6000), BigDecimal.valueOf(2000), LocalDateTime.now());

        when(transactionRepository.findByUser(user)).thenReturn(List.of(transaction1, transaction2));

        var transactions = transactionService.getUserTransactions();

        assertEquals(2, transactions.size());
        assertEquals("BTC", transactions.get(0).getCryptoSymbol());
    }

    @Test
    void testLogBuyTransaction() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);

        var transaction = new Transaction(null, user, "BTC", TransactionType.BUY, BigDecimal.valueOf(1), BigDecimal.valueOf(50000), BigDecimal.valueOf(50000), BigDecimal.ZERO, LocalDateTime.now());

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        transactionService.logBuyTransaction("BTC", BigDecimal.valueOf(1), BigDecimal.valueOf(50000), BigDecimal.valueOf(50000));
    }

    @Test
    void testLogSellTransaction() {
        when(userService.getCurrentAuthenticatedUser()).thenReturn(user);
        Transaction buyTransaction = new Transaction(null, user, "BTC", TransactionType.BUY, BigDecimal.valueOf(2), BigDecimal.valueOf(50000), BigDecimal.valueOf(100000), BigDecimal.ZERO, LocalDateTime.now());
        when(transactionRepository.findByUserAndCryptoSymbolAndType(user, "BTC", TransactionType.BUY))
                .thenReturn(Optional.of(buyTransaction));
        when(cryptoRepository.findBySymbol("BTC"))
                .thenReturn(Optional.of(new Crypto(1L, "BTC", BigDecimal.valueOf(60000))));
        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        transactionService.logSellTransaction("BTC", BigDecimal.valueOf(1), BigDecimal.valueOf(60000), BigDecimal.valueOf(60000));
    }


}
