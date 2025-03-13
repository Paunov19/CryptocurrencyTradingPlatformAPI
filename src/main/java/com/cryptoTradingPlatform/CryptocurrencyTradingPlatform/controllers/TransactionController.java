package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.controllers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.TransactionDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(
            summary = "Get all transactions for the current user",
            description = "Get all transactions with transaction details"
    )
    @GetMapping()
    public ResponseEntity<List<TransactionDTO>> getAllUserTransactions() {
        List<TransactionDTO> allUserTransactions = transactionService.getUserTransactions();
        return ResponseEntity.ok(allUserTransactions);
    }
}
