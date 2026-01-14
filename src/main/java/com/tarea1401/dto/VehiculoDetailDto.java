package com.tarea1401.dto;

import com.tarea1401.model.Estado;

public record VehiculoDetailDto(
        Long id,
        String matricula,
        String modelo,
        double kmActuales,
        Estado estado,
        Long asignacionActiva,
        Long conductorActivoId,
        String conductorActivoNombre,
        Long ultimoMantenimientoId,
        String ultimoMantenimientoFecha
    ) {
}
