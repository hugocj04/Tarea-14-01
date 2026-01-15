package com.tarea1401.controller;

import com.tarea1401.dto.CreateAsignacionRequest;
import com.tarea1401.model.Asignacion;
import com.tarea1401.model.Conductor;
import com.tarea1401.model.Estado;
import com.tarea1401.model.Vehiculo;
import com.tarea1401.repository.AsignacionRepository;
import com.tarea1401.repository.ConductorRepository;
import com.tarea1401.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class AsignacionController {

    private final AsignacionRepository asignacionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ConductorRepository conductorRepository;

    @PostMapping
    public ResponseEntity<Asignacion> crearAsignacion(@RequestBody CreateAsignacionRequest request) {
        boolean conductorTieneAsignacionActiva;
        Asignacion asignacion;

        Vehiculo vehiculo = vehiculoRepository.findById(request.vehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Conductor conductor = conductorRepository.findById(request.conductorId())
                .orElseThrow(() -> new RuntimeException("Conductor no encontrado"));

        if (vehiculo.getEstado() != Estado.DISPONIBLE) {
            throw new RuntimeException("El vehículo no está disponible");
        }

        conductorTieneAsignacionActiva = asignacionRepository.existsByConductorAndFechaFinIsNull(conductor);
        if (conductorTieneAsignacionActiva) {
            throw new RuntimeException("El conductor ya tiene una asignación activa");
        }

        asignacion = new Asignacion();
        asignacion.setVehiculo(vehiculo);
        asignacion.setConductor(conductor);
        asignacion.setFechaInicio(LocalDate.now());

        vehiculo.setEstado(Estado.ASIGNADO);
        vehiculoRepository.save(vehiculo);

        return ResponseEntity.ok(asignacionRepository.save(asignacion));
    }

    @PutMapping("asignaciones/{id}/cerrar")
    public ResponseEntity<Asignacion> cerrarAsignacion(@PathVariable Long id) {
        Vehiculo vehiculo;

        Asignacion asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignación no encontrada"));

        if (asignacion.getFechaFin() != null) {
            throw new RuntimeException("La asignación ya está cerrada");
        }

        asignacion.setFechaFin(LocalDate.now());

        vehiculo = asignacion.getVehiculo();
        vehiculo.setEstado(Estado.DISPONIBLE);
        vehiculoRepository.save(vehiculo);

        return ResponseEntity.ok(asignacionRepository.save(asignacion));
    }

}
