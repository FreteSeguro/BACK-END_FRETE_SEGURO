package br.com.seguro.frete.websocket;

import java.io.InputStream;
import java.util.List;

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

    @PostConstruct
    public void init() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("rota.json");
        List<GpsPosition> gpsPositions = objectMapper.readValue(inputStream, new TypeReference<>() {});
        gpsWebSocketHandler.setGpsPositions(gpsPositions);
    }

    @Scheduled(fixedRate = 5000)
    public void tick() {
        gpsWebSocketHandler.tick();
    }
}
