package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.TransactionDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Transaction;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class TransactionMapper {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'  'HH:mm:ss");

    public static TransactionDTO entityToDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUserId(transaction.getUser().getId());
        transactionDTO.setCryptoSymbol(transaction.getCryptoSymbol());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setQuantity(transaction.getQuantity());
        transactionDTO.setPricePerUnit(transaction.getPricePerUnit());
        transactionDTO.setTotalPrice(transaction.getTotalPrice());
        transactionDTO.setProfitOrLoss(transaction.getProfitOrLoss());
        transactionDTO.setTimestamp(transaction.getTimestamp().format(TIMESTAMP_FORMATTER));
        return transactionDTO;
    }

    public static Transaction toEntity(TransactionDTO transactionDTO, User user) {
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setCryptoSymbol(transactionDTO.getCryptoSymbol());
        transaction.setType(transactionDTO.getType());
        transaction.setQuantity(transactionDTO.getQuantity());
        transaction.setPricePerUnit(transactionDTO.getPricePerUnit());
        transaction.setTotalPrice(transactionDTO.getTotalPrice());
        transaction.setProfitOrLoss(transactionDTO.getProfitOrLoss());
        transaction.setTimestamp(LocalDateTime.parse(transactionDTO.getTimestamp(), TIMESTAMP_FORMATTER));
        return transaction;
    }
}
