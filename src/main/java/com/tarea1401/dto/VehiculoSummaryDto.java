package com.tarea1401.dto;

import com.tarea1401.model.Estado;

public record VehiculoSummaryDto(
        Long id,
        String matricula,
        String modelo,
        double kmActuales,
        Estado estad
    ) {
}
