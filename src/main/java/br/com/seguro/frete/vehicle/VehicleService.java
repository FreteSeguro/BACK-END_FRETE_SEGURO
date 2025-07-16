package br.com.seguro.frete.vehicle;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.seguro.frete.user.User;
import br.com.seguro.frete.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public Vehicle createVehicle(VehicleCreateDTO dto) {
        User user = findUserByIdOrThrow(dto.user());
        Vehicle vehicle = VehicleMapper.toEntity(dto, user);
        return vehicleRepository.save(vehicle);
    }

    public List<VehicleResponseDTO> findAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        return vehicles.stream()
            .map(VehicleMapper::toResponseDTO)
            .collect(Collectors.toList());
    } //TODO: muda depois para ser apenas uma listagem normal sem ser paginada

    public void updateVehicle(Long id, VehicleCreateDTO dto) {
        Vehicle vehicle = findVehicleByIdOrThrow(id);
        vehicle.setPlaca(dto.placa());
        vehicle.setMarca(dto.marca());
        vehicle.setModelo(dto.modelo());
        vehicle.setAno(dto.ano());
        vehicle.setCor(dto.cor());
        vehicle.setTipo(dto.tipo());
        vehicleRepository.save(vehicle);
    }
    
    public List<VehicleResponseDTO> findVehiclesByUserId(Long userId) {
        List<Vehicle> vehicles = vehicleRepository.findByUserId(userId);
        return vehicles.stream()
            .map(VehicleMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public VehicleResponseDTO findVehicleById(Long id) {
        Vehicle vehicle = findVehicleByIdOrThrow(id);
        return VehicleMapper.toResponseDTO(vehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = findVehicleByIdOrThrow(id);
        vehicleRepository.delete(vehicle);
    }

    private Vehicle findVehicleByIdOrThrow(Long id) {
        return vehicleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
    }

    private User findUserByIdOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
