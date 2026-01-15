package com.tarea1401.controller;

import com.tarea1401.dto.CreateMantenimientoRequest;
import com.tarea1401.model.Estado;
import com.tarea1401.model.Mantenimiento;
import com.tarea1401.model.Taller;
import com.tarea1401.model.Vehiculo;
import com.tarea1401.repository.MantenimientoRepository;
import com.tarea1401.repository.TallerRepository;
import com.tarea1401.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class MantenimientoController {

    private final MantenimientoRepository mantenimientoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final TallerRepository tallerRepository;

    @PostMapping
    public ResponseEntity<Mantenimiento> crearMantenimiento(@RequestBody CreateMantenimientoRequest request) {
        Mantenimiento mantenimiento = new Mantenimiento();

        Vehiculo vehiculo = vehiculoRepository.findById(request.vehiculoId())

                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Taller taller = tallerRepository.findById(request.tallerId())
                .orElseThrow(() -> new RuntimeException("Taller no encontrado"));

        if (vehiculo.getEstado() == Estado.ASIGNADO) {
            throw new RuntimeException("No se puede registrar mantenimiento, el vehículo está asignado");
        }

        if (request.kmEnRevision() < vehiculo.getKmActuales()) {
            throw new RuntimeException("El kilometraje del mantenimiento debe ser mayor o igual al actual");
        }

        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setTaller(taller);
        mantenimiento.setTipo(request.tipo());
        mantenimiento.setFecha(LocalDate.now());
        mantenimiento.setKmEnRevision(request.kmEnRevision());

        vehiculo.setKmActuales(request.kmEnRevision());
        vehiculo.setEstado(Estado.EN_MANTENIMIENTO);
        vehiculoRepository.save(vehiculo);

        return ResponseEntity.ok(mantenimientoRepository.save(mantenimiento));
    }

}
