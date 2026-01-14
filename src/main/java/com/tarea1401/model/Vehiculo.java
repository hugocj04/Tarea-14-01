package com.tarea1401.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matricula;
    private String modelo;
    private double kmActuales;
    private Estado estado;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Asignacion> asignaciones = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Mantenimiento> mantenimientos = new ArrayList<>();

    public void asignarVehiculo(Asignacion asignacion) {
        boolean vehiculoActivo = asignaciones.stream()
                .anyMatch(a -> a.getFechaFin() == null);
        Conductor conductor;
        boolean conductorActivo;

        if (asignacion == null) {
            throw new IllegalArgumentException("La asignación no puede ser nula.");
        }

        if (vehiculoActivo) {
            throw new IllegalStateException("El vehículo ya tiene una asignación activa.");
        }

        conductor = asignacion.getConductor();
        conductorActivo = conductor != null && conductor.getAsignaciones()
                .stream().anyMatch(a -> a.getFechaFin() == null);

        if (conductorActivo) {
            throw new IllegalStateException("El conductor ya tiene una asignación activa.");
        }

        asignacion.setVehiculo(this);
        asignaciones.add(asignacion);
        this.estado = Estado.ASIGNADO;
    }
}
