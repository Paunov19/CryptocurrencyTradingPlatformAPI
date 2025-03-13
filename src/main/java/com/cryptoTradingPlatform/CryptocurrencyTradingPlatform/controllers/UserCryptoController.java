package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.controllers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.UserCryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.payload.CryptoRequest;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.UserCryptoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/user-crypto")
public class UserCryptoController {

    @Autowired
    private UserCryptoService userCryptoService;

    @Operation(
            summary = "Get user portfolio",
            description = "Retrieve the current user's cryptocurrency portfolio."
    )
    @GetMapping("/portfolio")
    public ResponseEntity<List<UserCryptoDTO>> getUserPortfolio() {
        List<UserCryptoDTO> userCryptoPortfolio = userCryptoService.getUserPortfolio();
        return ResponseEntity.ok(userCryptoPortfolio);
    }

    @Operation(
            summary = "Get total portfolio value",
            description = "Retrieve the total value of the current user's portfolio."
    )
    @GetMapping("/portfolio-value")
    public ResponseEntity<BigDecimal> getTotalPortfolioValue() {
        BigDecimal totalPortfolioValue = userCryptoService.getTotalPortfolioValue();
        return ResponseEntity.ok(totalPortfolioValue);
    }

    @Operation(
            summary = "Add crypto to portfolio",
            description = "Buy a specified quantity of a cryptocurrency and add it to the user's portfolio."
    )

    @PostMapping("/buy")
    public ResponseEntity<String> addCryptoToPortfolio(@RequestBody CryptoRequest cryptoRequest) {
        userCryptoService.addCryptoToPortfolio(cryptoRequest.symbol(), cryptoRequest.quantity());
        return ResponseEntity.ok("Successfully bought: " + cryptoRequest.quantity() + " " + cryptoRequest.symbol());
    }

    @Operation(
            summary = "Remove crypto from portfolio",
            description = "Sell a specified quantity of a cryptocurrency from the user's portfolio."
    )
    @PostMapping("/sell")
    public ResponseEntity<String> removeCryptoFromPortfolio(@RequestBody CryptoRequest cryptoRequest) {
        userCryptoService.removeCryptoFromPortfolio(cryptoRequest.symbol(), cryptoRequest.quantity());
        return ResponseEntity.ok("Successfully sold: " + cryptoRequest.quantity() + " " + cryptoRequest.symbol());
    }

}
