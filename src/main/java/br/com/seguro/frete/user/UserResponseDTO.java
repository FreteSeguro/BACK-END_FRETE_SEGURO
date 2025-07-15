package br.com.seguro.frete.user;

import java.time.Instant;
import java.util.Set;

import br.com.seguro.frete.enums.Role;
import br.com.seguro.frete.vehicle.VehicleResponseDTO;

public record UserResponseDTO(
    Long id,
    String name,
    String email,
    String phone,
    Role role,
    Instant createdAt,
    Set<VehicleResponseDTO> vehicles
) {
}
