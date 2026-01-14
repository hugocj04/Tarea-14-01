package com.tarea1401.dto;

import com.tarea1401.model.Mantenimiento;
import com.tarea1401.model.Vehiculo;

public record VehiculoConUltimoMantenimiento(
        Vehiculo vehiculo,
        Mantenimiento ultimoMantenimiento
    ) {
}
