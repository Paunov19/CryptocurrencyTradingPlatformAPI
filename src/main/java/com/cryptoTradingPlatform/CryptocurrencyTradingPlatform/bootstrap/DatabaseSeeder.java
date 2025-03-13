package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.bootstrap;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.UserCrypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserCryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserCryptoRepository userCryptoRepository;
    @Autowired
    private CryptoRepository cryptoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final BigDecimal SET_BALANCE = BigDecimal.valueOf(10000);

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            seedUsersAndWallets();
        }
    }

    private void seedUsersAndWallets() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setEmail("user1@test.com");
        user1.setUsername("Test User1");
        user1.setPassword(passwordEncoder.encode("password1"));
        user1.setBalance(SET_BALANCE);
        user1.setPortfolio(new ArrayList<>());
        users.add(user1);

        User user2 = new User();
        user2.setEmail("user2@test.com");
        user2.setUsername("Test User2");
        user2.setPassword(passwordEncoder.encode("password2"));
        user2.setBalance(SET_BALANCE);
        user2.setPortfolio(new ArrayList<>());
        users.add(user2);

        User user3 = new User();
        user3.setEmail("user3@test.com");
        user3.setUsername("Test User3");
        user3.setPassword(passwordEncoder.encode("password3"));
        user3.setBalance(SET_BALANCE);
        user3.setPortfolio(new ArrayList<>());
        users.add(user3);

        userRepository.saveAll(users);

        Crypto btc = cryptoRepository.findBySymbol("BTC/USD").orElseGet(() -> {
            Crypto newBtc = new Crypto();
            newBtc.setSymbol("BTC/USD");
            newBtc.setPrice(BigDecimal.valueOf(50000));
            return cryptoRepository.save(newBtc);
        });

        Crypto eth = cryptoRepository.findBySymbol("ETH/USD").orElseGet(() -> {
            Crypto newEth = new Crypto();
            newEth.setSymbol("ETH/USD");
            newEth.setPrice(BigDecimal.valueOf(20000));
            return cryptoRepository.save(newEth);
        });

        for (User user : users) {
            UserCrypto userCrypto = new UserCrypto();
            userCrypto.setUser(user);
            userCrypto.setCrypto(btc);
            userCrypto.setQuantity(BigDecimal.valueOf(10));
            userCrypto.setTotalAmount(btc.getPrice().multiply(userCrypto.getQuantity()));
            userCryptoRepository.save(userCrypto);
            user.getPortfolio().add(userCrypto);
        }

        UserCrypto additionalETH = new UserCrypto();
        additionalETH.setUser(user1);
        additionalETH.setCrypto(eth);
        additionalETH.setQuantity(BigDecimal.valueOf(5));
        additionalETH.setTotalAmount(eth.getPrice().multiply(additionalETH.getQuantity()));
        userCryptoRepository.save(additionalETH);
        user1.getPortfolio().add(additionalETH);


        System.out.println("Database seeded with initial data.");
    }
}
