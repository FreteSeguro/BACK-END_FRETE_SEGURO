package br.com.seguro.frete.gps;

public record GpsCreateDTO(
    Double latitude,
    Double longitude,
    Double altitude,
    Double speed,
    Long vehicleId
) {
    
}
