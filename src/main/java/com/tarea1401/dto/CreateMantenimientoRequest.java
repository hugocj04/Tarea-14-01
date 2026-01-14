package com.tarea1401.dto;

import java.time.LocalDate;

public record CreateMantenimientoRequest(
        Long vehiculoId,
        Long tallerId,
        String tipo,
        LocalDate fecha,
        double kmEnRevision
    ) {
}
