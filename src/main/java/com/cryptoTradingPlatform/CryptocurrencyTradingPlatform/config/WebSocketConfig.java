package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.config;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl.CryptoPriceWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private CryptoPriceWebSocketHandler cryptoPriceWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(cryptoPriceWebSocketHandler, "/ws/prices").setAllowedOrigins("*");
    }
}
