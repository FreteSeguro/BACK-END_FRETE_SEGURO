package br.com.seguro.frete.websocket;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

@Data
public class GpsSessionState {
    private boolean started = false;
    private AtomicInteger currentIndex = new AtomicInteger(0);
    private int firstBatchSize = new java.util.Random().nextInt(10) + 1;
}
