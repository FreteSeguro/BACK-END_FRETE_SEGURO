package br.com.seguro.frete.gps;

import java.time.Instant;

import br.com.seguro.frete.enums.StatusGps;
import br.com.seguro.frete.vehicle.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "gps")
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Gps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gps_id", unique = true, nullable = false)
    private Long id;//TODO: este campo sera o numero do chip de telefone do gps

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "altitude")
    private Double altitude;

    @Column(name = "speed")
    private Double speed;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusGps status;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;


}
