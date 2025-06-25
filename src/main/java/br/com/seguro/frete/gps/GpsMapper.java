package br.com.seguro.frete.gps;

import br.com.seguro.frete.vehicle.Vehicle;
import br.com.seguro.frete.vehicle.VehicleResponseDTO;

public class GpsMapper {

    public static Gps toEntity(GpsCreateDTO dto, Vehicle vehicle) {
        Gps gps = new Gps();
        gps.setLatitude(dto.latitude());
        gps.setLongitude(dto.longitude());
        gps.setAltitude(dto.altitude());
        gps.setSpeed(dto.speed());
        gps.setVehicle(vehicle);

        return gps;
    }

    public static GpsResponseDTO toResponseDTO(Gps gps) {
        return new GpsResponseDTO(
            gps.getId(),
            gps.getLatitude(),
            gps.getLongitude(),
            gps.getAltitude(),
            gps.getSpeed(),
            new VehicleResponseDTO(
                gps.getVehicle().getId(),
                gps.getVehicle().getPlaca(),
                gps.getVehicle().getMarca(),
                gps.getVehicle().getModelo(),
                gps.getVehicle().getAno(),
                gps.getVehicle().getCor(),
                gps.getVehicle().getTipo()
            )//TODO:: retornar entidade
        );
    }

}
