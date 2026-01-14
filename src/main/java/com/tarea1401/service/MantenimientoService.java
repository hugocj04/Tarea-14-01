package com.tarea1401.service;

import com.tarea1401.model.Estado;
import com.tarea1401.model.Mantenimiento;
import com.tarea1401.model.Vehiculo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MantenimientoService {

    public Mantenimiento registrarMantenimiento(Mantenimiento mantenimiento) {
        Vehiculo vehiculo;

        if (mantenimiento == null || mantenimiento.getVehiculo() == null) {
            throw new IllegalArgumentException("Mantenimiento o vehículo no puede ser nulo.");
        }

        vehiculo = getVehiculo(mantenimiento);
        mantenimiento.setVehiculo(vehiculo);
        mantenimiento.setFecha(LocalDate.now());

        return mantenimiento;
    }

    private Vehiculo getVehiculo(Mantenimiento mantenimiento) {
        Vehiculo vehiculo = mantenimiento.getVehiculo();
        double kmActual, kmMantenimiento;

        if (vehiculo.getEstado() == Estado.ASIGNADO) {
            throw new IllegalStateException("No se puede registrar mantenimiento: el vehículo está asignado.");
        }

        kmActual = vehiculo.getKmActuales();
        kmMantenimiento = mantenimiento.getKmEnRevision();

        if (kmMantenimiento < kmActual) {
            throw new IllegalArgumentException("El kilometraje del mantenimiento debe ser mayor o igual al km actual del vehículo.");
        }

        vehiculo.setKmActuales(kmMantenimiento);
        return vehiculo;
    }

}
