package br.com.seguro.frete.websocket;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GpsScheduler {

    private final GpsWebSocketHandler gpsWebSocketHandler;

    @Scheduled(fixedRate = 5000)
    public void sendGpsData() throws Exception {
        String gpsData = "GPS Data at " + System.currentTimeMillis();
        System.out.println("📡 Enviando: " + gpsData);
        gpsWebSocketHandler.broadcast(gpsData);
    }
}