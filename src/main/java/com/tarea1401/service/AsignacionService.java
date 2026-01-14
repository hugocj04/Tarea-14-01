package com.tarea1401.service;

import com.tarea1401.model.Asignacion;
import com.tarea1401.model.Estado;
import com.tarea1401.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AsignacionService {

    public Asignacion cerrarAsignacion(Asignacion asignacion) {
        Vehiculo vehiculo;

        if (asignacion == null) {
            throw new IllegalArgumentException("La asignación no puede ser nula.");
        }

        if (asignacion.getFechaFin() != null) {
            throw new IllegalArgumentException("La asignación ya está cerrada.");
        }

        vehiculo = asignacion.getVehiculo();
        if (vehiculo == null) {
            throw new IllegalArgumentException("La asignación no tiene vehículo asociado.");
        }

        asignacion.setFechaFin(LocalDate.now());
        vehiculo.setEstado(Estado.DISPONIBLE);

        return asignacion;
    }
}
