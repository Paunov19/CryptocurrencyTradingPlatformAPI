package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl;

import com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class CryptoPriceWebSocketHandler extends TextWebSocketHandler {
    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    @Autowired
    private CryptoService cryptoService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("New client connected: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    public void broadcastPriceUpdate(String symbol, BigDecimal price) {
        cryptoService.updateCryptoPrice(symbol, price);
        String message = "{symbol: " + symbol + "\\price: " + price + "}";
        System.out.println("Broadcasting: " + message); // 🔍 Проверка

        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        }
    }

}
