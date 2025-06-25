package br.com.seguro.frete.gps;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GpsRepository extends JpaRepository<Gps, Long> {
    Page<Gps> findAll(Pageable pageable);
}
