package br.com.seguro.frete.gps;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.seguro.frete.vehicle.Vehicle;
import br.com.seguro.frete.vehicle.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GpsService {

    private final GpsRepository gpsRepository;
    private final VehicleRepository vehicleRepository;

    public Gps createGps(GpsCreateDTO dto) {
        Vehicle vehicle = findVehicleByIdOrThrow(dto.vehicleId());
        Gps gps = GpsMapper.toEntity(dto, vehicle);
        return gpsRepository.save(gps);
    }

    //list all
    public List<GpsResponseDTO> findAllGps() {
        List<Gps> gpsList = gpsRepository.findAll();
        return gpsList.stream()
            .map(GpsMapper::toResponseDTO)
            .collect(Collectors.toList());
    }

    public Vehicle findVehicleByIdOrThrow(Long vehicleId) {
        return vehicleRepository.findById(vehicleId)
            .orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
    }
}
