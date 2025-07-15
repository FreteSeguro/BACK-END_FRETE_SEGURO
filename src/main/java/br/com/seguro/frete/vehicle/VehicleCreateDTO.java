package br.com.seguro.frete.vehicle;

import br.com.seguro.frete.enums.VehicleType;


public record VehicleCreateDTO(
    String placa,
    String marca,
    String modelo,
    Integer ano,
    String cor,
    VehicleType tipo,
    Long user
) {
}
