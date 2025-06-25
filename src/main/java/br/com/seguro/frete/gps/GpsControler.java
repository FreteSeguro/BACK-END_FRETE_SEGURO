package br.com.seguro.frete.gps;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/gps")
@RequiredArgsConstructor
public class GpsControler {
    
    private final GpsService gpsService;
    private final GpsRepository gpsRepository;

    @PostMapping
    public ResponseEntity<Map<String, Long>> createGps(@Validated @RequestBody  GpsCreateDTO dto, UriComponentsBuilder uriBuilder) {
        Gps gps = gpsService.createGps(dto);
        var uri = uriBuilder.path("/gps/{id}").buildAndExpand(gps.getId()).toUri();
        return ResponseEntity.created(uri).body(Map.of("id", gps.getId()));
    }

    @GetMapping
    public ResponseEntity<Page<GpsResponseDTO>> findAllGps(Pageable pageable) {
        Page<Gps> response = gpsRepository.findAll(pageable);
        return ResponseEntity.ok(response.map(GpsMapper::toResponseDTO));
    }
}
