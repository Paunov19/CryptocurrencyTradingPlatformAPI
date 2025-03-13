package com.cryptoTradingPlatform.CryptocurrencyTradingPlatform.services.Impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;


@Service
public class KrakenWebSocketServiceImpl {
    private static final String KRAKEN_WS_URL = "wss://ws.kraken.com/v2";
    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private CryptoPriceWebSocketHandler cryptoPriceWebSocketHandler;

    @PostConstruct
    public void startConnection() {
        connect();
    }

    public void connect() {
        Request request = new Request.Builder().url(KRAKEN_WS_URL).build();
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                System.out.println("Connected to Kraken WebSocket API");
                subscribeToTicker(webSocket);
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                System.out.println("Received message from Kraken: " + text);
                handleIncomingMessage(text);
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                System.err.println("Error in WebSocket connection: " + t.getMessage());
            }
        });
    }

    public void subscribeToTicker(WebSocket webSocket) {
        try {
            String jsonMessage = """
                        {
                            "method": "subscribe",
                            "params": {
                                "channel": "ticker",
                                "symbol": [
                                    "BTC/USD", "ETH/USD", "XRP/USD", "SOL/USD", "FIL/USD", "ADA/USD", "DOGE/USD", "AVAX/USD", "DOT/USD","SHIB/USD", 
                                    "MATIC/USD","TRX/USD", "BCH/USD",  "LINK/USD","XLM/USD", "MANA/USD", "CRV/USD","LTC/USD", "LUNA/USD", "AAVE/USD"
                                ]
                            }
                        }
                    """;

            webSocket.send(jsonMessage);

        } catch (Exception e) {
            System.err.println("Error subscribing to top 20 cryptos: " + e.getMessage());
        }
    }


    public void handleIncomingMessage(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            if (jsonNode.has("channel") && jsonNode.get("channel").asText().equals("heartbeat")) {
                return;
            }
            System.out.println("Parsed JSON: " + jsonNode.toString());
            if (jsonNode.has("channel") && jsonNode.get("channel").asText().equals("ticker")) {
                JsonNode tickerData = jsonNode.get("data").get(0);
                String symbol = tickerData.get("symbol").asText();
                String lastTradePrice = tickerData.get("last").asText();
                BigDecimal currentPrice = new BigDecimal(lastTradePrice);
                System.out.println("Updating price: " + symbol + " - " + currentPrice);

                cryptoPriceWebSocketHandler.broadcastPriceUpdate(symbol, currentPrice);
            }
        } catch (IOException e) {
            System.err.println("Error parsing message: " + e.getMessage());
        }
    }
}
