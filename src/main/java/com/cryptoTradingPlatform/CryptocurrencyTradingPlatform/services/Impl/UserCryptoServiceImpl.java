package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserCryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.CryptoNotFoundException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.InsufficientCryptoException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.InsufficientFundsException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.exceptions.UserNotOwnThisCryptoException;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers.UserCryptoMapper;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.UserCrypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserCryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.TransactionService;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.UserCryptoService;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserCryptoServiceImpl implements UserCryptoService {
    @Autowired
    private UserCryptoRepository userCryptoRepository;
    @Autowired
    private CryptoRepository cryptoRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private TransactionService transactionService;

    @Override
    public List<UserCryptoDTO> getUserPortfolio() {
        User user = userService.getCurrentAuthenticatedUser();
        List<UserCrypto> userCryptoList = userCryptoRepository.findByUser(user);
        return userCryptoList.stream().map(UserCryptoMapper::entityToDTO).toList();
    }

    @Override
    public BigDecimal getTotalPortfolioValue() {
        User user = userService.getCurrentAuthenticatedUser();
        List<UserCrypto> userCryptoList = userCryptoRepository.findByUser(user);

        BigDecimal totalPortfolioValue = BigDecimal.ZERO;
        for (UserCrypto userCrypto : userCryptoList) {
            totalPortfolioValue = totalPortfolioValue.add(userCrypto.getTotalAmount());
        }
        return totalPortfolioValue;
    }

    @Override
    public void addCryptoToPortfolio(String symbol, BigDecimal quantity) {
        User user = userService.getCurrentAuthenticatedUser();
        Crypto crypto = cryptoRepository.findBySymbol(symbol)
                .orElseThrow(() -> new CryptoNotFoundException(symbol));

        BigDecimal totalPrice = crypto.getPrice().multiply(quantity);
        if (user.getBalance().compareTo(totalPrice) < 0) {
            throw new InsufficientFundsException(user.getUsername());
        }
        user.setBalance(user.getBalance().subtract(totalPrice));

        UserCrypto userCrypto = userCryptoRepository.findByUserAndCrypto(user, crypto)
                .orElseGet(() -> new UserCrypto(null, user, crypto, BigDecimal.ZERO, BigDecimal.ZERO));
        userCrypto.setQuantity(userCrypto.getQuantity().add(quantity));
        userCrypto.setTotalAmount(userCrypto.getTotalAmount().add(totalPrice));

        transactionService.logBuyTransaction(crypto.getSymbol(), quantity, crypto.getPrice(), totalPrice);
        userCryptoRepository.save(userCrypto);

    }

    @Override
    public void removeCryptoFromPortfolio(String symbol, BigDecimal quantity) {
        User user = userService.getCurrentAuthenticatedUser();
        Crypto crypto = cryptoRepository.findBySymbol(symbol)
                .orElseThrow(() -> new CryptoNotFoundException(symbol));

        UserCrypto userCrypto = userCryptoRepository.findByUserAndCrypto(user, crypto)
                .orElseThrow(() -> new UserNotOwnThisCryptoException(user.getUsername(), symbol));

        if (userCrypto.getQuantity().compareTo(quantity) < 0) {
            throw new InsufficientCryptoException(user.getUsername(), symbol);
        }
        BigDecimal totalSalePrice = crypto.getPrice().multiply(quantity);
        user.setBalance(user.getBalance().add(totalSalePrice));
        userCrypto.setQuantity(userCrypto.getQuantity().subtract(quantity));
        userCrypto.setTotalAmount(userCrypto.getTotalAmount().subtract(totalSalePrice));

        transactionService.logSellTransaction(crypto.getSymbol(), quantity, crypto.getPrice(), totalSalePrice);

        if (userCrypto.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            userCryptoRepository.delete(userCrypto);
        } else {
            userCryptoRepository.save(userCrypto);
        }
    }

}
