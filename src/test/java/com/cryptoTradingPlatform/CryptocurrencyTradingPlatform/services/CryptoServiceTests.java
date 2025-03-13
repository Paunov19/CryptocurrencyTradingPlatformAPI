package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.dtos.CryptoDTO;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.models.Crypto;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.repositories.CryptoRepository;
import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl.CryptoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CryptoServiceTests {

    @Mock
    private CryptoRepository cryptoRepository;
    @InjectMocks
    private CryptoServiceImpl cryptoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCryptos() {
        when(cryptoRepository.findAll()).thenReturn(List.of(
                new Crypto(1L, "BTC", BigDecimal.valueOf(30000)),
                new Crypto(2L, "ETH", BigDecimal.valueOf(2000))
        ));

        List<CryptoDTO> cryptos = cryptoService.getAllCryptos();

        assertEquals(2, cryptos.size());
        assertEquals("BTC", cryptos.get(0).getSymbol());
    }

    @Test
    void testUpdateCryptoPrice() {
        var crypto = new Crypto(1L, "BTC", BigDecimal.valueOf(30000));
        when(cryptoRepository.findBySymbol("BTC")).thenReturn(Optional.of(crypto));

        cryptoService.updateCryptoPrice("BTC", BigDecimal.valueOf(35000));

        assertEquals(BigDecimal.valueOf(35000), crypto.getPrice());
    }
}
