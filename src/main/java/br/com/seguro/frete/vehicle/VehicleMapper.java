package br.com.seguro.frete.vehicle;

public class VehicleMapper {

    public static Vehicle toEntity(VehicleCreateDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setPlaca(dto.placa());
        vehicle.setMarca(dto.marca());
        vehicle.setModelo(dto.modelo());
        vehicle.setAno(dto.ano());
        vehicle.setCor(dto.cor());
        vehicle.setTipo(dto.tipo());
        return vehicle;
    }

    public static VehicleResponseDTO toResponseDTO(Vehicle vehicle) {
        return new VehicleResponseDTO(
            vehicle.getId(),
            vehicle.getPlaca(),
            vehicle.getMarca(),
            vehicle.getModelo(),
            vehicle.getAno(),
            vehicle.getCor(),
            vehicle.getTipo()
        );
    }
}
