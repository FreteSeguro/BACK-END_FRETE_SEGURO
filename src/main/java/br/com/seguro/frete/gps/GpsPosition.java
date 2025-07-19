package br.com.seguro.frete.gps;

import lombok.Data;

@Data
public class GpsPosition {
    private Double latitude;
    private Double longitude;
    private Double altitude;
    private Double speed;
}
