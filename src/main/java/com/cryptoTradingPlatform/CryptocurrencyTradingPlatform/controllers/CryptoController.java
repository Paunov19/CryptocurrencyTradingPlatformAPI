package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.controllers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.CryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.CryptoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cryptocurrencies")
public class CryptoController {

    @Autowired
    private CryptoService cryptoService;

    @Operation(
            summary = "Get all cryptocurrencies",
            description = "Get all cryptocurrencies with their details in real-time"
    )
    @GetMapping()
    public ResponseEntity<List<CryptoDTO>> getALLCryptoCurrencies() {
        List<CryptoDTO> allCryptos = cryptoService.getAllCryptos();
        return ResponseEntity.ok(allCryptos);
    }

}

