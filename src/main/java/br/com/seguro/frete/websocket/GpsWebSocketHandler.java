package br.com.seguro.frete.websocket;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.seguro.frete.gps.GpsPosition;

@Component
public class GpsWebSocketHandler extends TextWebSocketHandler {

    private final Map<WebSocketSession, GpsSessionState> sessionStates = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<GpsPosition> gpsPositions;

    public void setGpsPositions(List<GpsPosition> positions) {
        this.gpsPositions = positions;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionStates.put(session, new GpsSessionState());
        System.out.println("🔌 Cliente conectado: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionStates.remove(session);
        System.out.println("❌ Cliente desconectado: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        GpsSessionState state = sessionStates.get(session);

        if (payload.equalsIgnoreCase("start")) {
            System.out.println("▶️ Iniciando envio para: " + session.getId());
            state.setStarted(true);
            state.setCurrentIndex(new AtomicInteger(0));
            state.setFirstBatchSize(new Random().nextInt(10) + 1);
        } else if (payload.equalsIgnoreCase("stop")) {
            System.out.println("⏹️ Parando envio para: " + session.getId());
            state.setStarted(false);
        }
    }

    public void tick() {
        for (Map.Entry<WebSocketSession, GpsSessionState> entry : sessionStates.entrySet()) {
            WebSocketSession session = entry.getKey();
            GpsSessionState state = entry.getValue();

            if (!session.isOpen() || !state.isStarted() || gpsPositions == null) continue;

            int current = state.getCurrentIndex().get();
            int count = (current == 0) ? state.getFirstBatchSize() : 1;
            int end = Math.min(current + count, gpsPositions.size());

            if (current >= gpsPositions.size()) {
                System.out.println("✅ Rota completa para " + session.getId());
                state.setStarted(false);
                continue;
            }

            List<GpsPosition> toSend = gpsPositions.subList(current, end);

            try {
                String json = objectMapper.writeValueAsString(toSend);
                session.sendMessage(new TextMessage(json));
                System.out.println("📤 Enviando para " + session.getId() + ": " + json);
            } catch (Exception e) {
                System.err.println("Erro ao enviar: " + e.getMessage());
            }

            state.getCurrentIndex().set(end);
        }
    }
}

