package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.CryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.mappers.CryptoMapper;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CryptoServiceImpl implements CryptoService {

    @Autowired
    private CryptoRepository cryptoRepository;

    @Override
    public List<CryptoDTO> getAllCryptos() {
        List<Crypto> cryptos = cryptoRepository.findAll();
        return cryptos.stream().map(CryptoMapper::entityToDTO).toList();
    }

    @Override
    @Transactional
    public void updateCryptoPrice(String symbol, BigDecimal newPrice) {
        Crypto crypto = cryptoRepository.findBySymbol(symbol)
                .orElseGet(() -> new Crypto(null, symbol, newPrice));
        crypto.setPrice(newPrice);
        cryptoRepository.save(crypto);
    }
}
