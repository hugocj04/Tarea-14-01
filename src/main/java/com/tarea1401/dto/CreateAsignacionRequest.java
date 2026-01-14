package com.tarea1401.dto;

import java.time.LocalDate;

public record CreateAsignacionRequest(
        Long vehiculoId,
        Long conductorId,
        LocalDate fechaInicio
    ) {
}
