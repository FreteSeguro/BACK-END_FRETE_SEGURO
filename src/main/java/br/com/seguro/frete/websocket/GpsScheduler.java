package br.com.seguro.frete.websocket;

import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.seguro.frete.gps.GpsPosition;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GpsScheduler {

    private final GpsWebSocketHandler gpsWebSocketHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<GpsPosition> gpsPositions;
    private final AtomicInteger currentIndex = new AtomicInteger(0);
    private boolean firstRun = true;

    @PostConstruct
    public void init() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rota.json");
        gpsPositions = objectMapper.readValue(inputStream, new TypeReference<>() {});
    }

    @Scheduled(fixedRate = 5000)
    public void sendGpsData() throws Exception {
        if (currentIndex.get() >= gpsPositions.size()) {
            System.out.println("✅ Rota completa.");
            return;
        }

        int start = currentIndex.get();
        int count;

        if (firstRun) {
            count = new Random().nextInt(10) + 1; // de 1 a 10
            firstRun = false;
        } else {
            count = 1;
        }

        int end = Math.min(start + count, gpsPositions.size());
        List<GpsPosition> toSend = gpsPositions.subList(start, end);
        String json = objectMapper.writeValueAsString(toSend);

        System.out.println("📡 Enviando: " + json);
        gpsWebSocketHandler.broadcast(json);

        currentIndex.set(end);
    }
}