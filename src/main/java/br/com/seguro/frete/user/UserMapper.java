package br.com.seguro.frete.user;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.seguro.frete.vehicle.VehicleResponseDTO;

public class UserMapper {

    public static User toEntity(UserCreateDTO user) {
        User dto = new User();
        dto.setId(user.id());
        dto.setName(user.name());
        dto.setEmail(user.email());
        dto.setPhone(user.phone());
        dto.setRole(user.role());
        return dto;
    }

    public static UserResponseDTO toResponseDTO(User user) {
        Set<VehicleResponseDTO> vehicles = user.getVehicles().stream()
            .map(vehicle -> new VehicleResponseDTO(
                vehicle.getId(),
                vehicle.getPlaca(),
                vehicle.getMarca(),
                vehicle.getModelo(),
                vehicle.getAno(),
                vehicle.getCor(),
                vehicle.getTipo()
            )).collect(Collectors.toSet());
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPhone(),
            user.getRole(),
            user.getCreatedAt(),
            vehicles
        );
    }
}
