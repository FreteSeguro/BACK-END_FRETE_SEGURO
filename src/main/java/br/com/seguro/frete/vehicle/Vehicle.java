package br.com.seguro.frete.vehicle;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import br.com.seguro.frete.enums.VehicleType;
import br.com.seguro.frete.gps.Gps;
//import br.com.seguro.frete.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Table(name = "vehicle")
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id", unique = true, nullable = false)
    private Long id ;

    @Column(name = "placa")
    private String placa;

    @Column(name = "marca")
    private String marca;
    
    @Column(name = "modelo")
    private String modelo;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "cor")
    private String cor;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private VehicleType tipo;

    @Column(name = "created_at")
    private Instant createdAt = Instant.now();

    //@OneToMany(mappedBy = "vehicle")
    @Column(name = "users")
    private Set<Long> users = new HashSet<>();

    @OneToOne(mappedBy = "vehicle")
    private Gps gps;


}
