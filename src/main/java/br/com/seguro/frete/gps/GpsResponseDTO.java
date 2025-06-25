package br.com.seguro.frete.gps;

import br.com.seguro.frete.vehicle.VehicleResponseDTO;

public record GpsResponseDTO(
    Long id,
    Double latitude,
    Double longitude,
    Double altitude,
    Double speed,
    VehicleResponseDTO vehicle
) {
    
}
