package br.com.seguro.frete.vehicle;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    
    private final VehicleRepository vehicleRepository;
    private final VehicleService vehicleService;

    @PostMapping
    public ResponseEntity<Map<String, Long>> createVehicle (@Validated @RequestBody VehicleCreateDTO dto, UriComponentsBuilder uriBuilder) {
        Vehicle vehicle = vehicleService.createVehicle(dto);
        var uri = uriBuilder.path("/vehicles/{id}").buildAndExpand(vehicle.getId()).toUri();
        return ResponseEntity.created(uri).body(Map.of("id", vehicle.getId()));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDTO>> findAllVehicles(Pageable pageable) {
        Page<Vehicle> response = vehicleRepository.findAll(pageable);
        return ResponseEntity.ok(response.map(VehicleMapper::toResponseDTO));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<List<VehicleResponseDTO>> findVehiclesByUserId(@PathVariable Long userId) {
        List<VehicleResponseDTO> vehicles = vehicleService.findVehiclesByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> findVehicleById(@PathVariable Long id) {
        VehicleResponseDTO vehicle = vehicleService.findVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }
}
