package com.tarea1401.controller;

import com.tarea1401.dto.VehiculoSummaryDto;
import com.tarea1401.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;

    @GetMapping
    public ResponseEntity<Page<VehiculoSummaryDto>> listarVehiculos(Pageable pageable) {
        Page<VehiculoSummaryDto> vehiculos = vehiculoRepository.findAll(pageable)
                .map(v -> new VehiculoSummaryDto(
                        v.getId(),
                        v.getMatricula(),
                        v.getModelo(),
                        v.getKmActuales(),
                        v.getEstado()
                ));

        return ResponseEntity.ok(vehiculos);
    }

}
