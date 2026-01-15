package com.tarea1401.controller;

import com.tarea1401.dto.VehiculoSummaryDto;
import com.tarea1401.model.Asignacion;
import com.tarea1401.model.Conductor;
import com.tarea1401.repository.AsignacionRepository;
import com.tarea1401.repository.ConductorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ConductorController {

    private final ConductorRepository conductorRepository;
    private final AsignacionRepository asignacionRepository;

    @GetMapping("/conductores/{id}/vehiculo-activo")
    public ResponseEntity<VehiculoSummaryDto> obtenerVehiculoActivo(@PathVariable Long id) {
        Conductor conductor = conductorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        Asignacion asignacionActiva = asignacionRepository.findByConductorAndFechaFinIsNull(conductor)
                .orElseThrow(() -> new RuntimeException("El conductor no tiene vehículo asignado"));

        VehiculoSummaryDto dto = new VehiculoSummaryDto(
                asignacionActiva.getVehiculo().getId(),
                asignacionActiva.getVehiculo().getMatricula(),
                asignacionActiva.getVehiculo().getModelo(),
                asignacionActiva.getVehiculo().getKmActuales(),
                asignacionActiva.getVehiculo().getEstado()
        );

        return ResponseEntity.ok(dto);
    }

}
