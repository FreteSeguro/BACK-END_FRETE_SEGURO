package br.com.seguro.frete.vehicle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public Vehicle createVehicle(VehicleCreateDTO dto) {
        Vehicle vehicle = VehicleMapper.toEntity(dto);
        return vehicleRepository.save(vehicle);
    }

    public List<VehicleResponseDTO> findAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
            .map(VehicleMapper::toResponseDTO)
            .collect(Collectors.toList());
    }
    
}
