package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.TransactionDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.CryptoNotFoundException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.InsufficientCryptoException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers.TransactionMapper;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Transaction;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.TransactionType;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.TransactionRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.TransactionService;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<TransactionDTO> getUserTransactions() {
        User user = userService.getCurrentAuthenticatedUser();
        List<Transaction> transactions = transactionRepository.findByUser(user);
        return transactions.stream().map(TransactionMapper::entityToDTO).toList();
    }

    @Override
    @Transactional
    public void logBuyTransaction(String symbol, BigDecimal quantity, BigDecimal price, BigDecimal totalAmount) {
        User user = userService.getCurrentAuthenticatedUser();
        Transaction transaction = new Transaction(null, user, symbol, TransactionType.BUY, quantity, price, totalAmount, BigDecimal.ZERO, LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void logSellTransaction(String symbol, BigDecimal quantity, BigDecimal sellPrice, BigDecimal totalSalePrice) {
        User user = userService.getCurrentAuthenticatedUser();
        Crypto crypto = cryptoRepository.findBySymbol(symbol)
                .orElseThrow(() -> new CryptoNotFoundException(symbol));

        List<Transaction> buyTransactions = transactionRepository.findByUserAndCryptoSymbolAndType(user, crypto.getSymbol(), TransactionType.BUY).stream().toList();

        if (buyTransactions.isEmpty()) {
            throw new IllegalArgumentException("No purchase transactions found for this user and crypto symbol");
        }

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal remainingQuantity = quantity;

        for (Transaction buyTransaction : buyTransactions) {
            if (remainingQuantity.compareTo(BigDecimal.ZERO) <= 0) break;

            BigDecimal availableQuantity = buyTransaction.getQuantity();
            BigDecimal usedQuantity = availableQuantity.min(remainingQuantity);

            totalCost = totalCost.add(usedQuantity.multiply(buyTransaction.getPricePerUnit()));
            remainingQuantity = remainingQuantity.subtract(usedQuantity);
        }

        if (remainingQuantity.compareTo(BigDecimal.ZERO) > 0) {
            throw new InsufficientCryptoException(user.getUsername(), symbol);
        }

        BigDecimal profitOrLoss = totalCost.subtract(totalSalePrice);

        Transaction transaction = new Transaction(null, user, symbol, TransactionType.SELL, quantity, sellPrice, totalSalePrice, profitOrLoss, LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}