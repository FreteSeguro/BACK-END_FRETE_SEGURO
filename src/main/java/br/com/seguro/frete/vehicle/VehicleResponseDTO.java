package br.com.seguro.frete.vehicle;

import br.com.seguro.frete.enums.VehicleType;

public record VehicleResponseDTO(
    Long id,
    String placa,
    String marca,
    String modelo,
    Integer ano,
    String cor,
    VehicleType tipo
    //TODO: ADICIONAR USUARIOS
) {
    
}
