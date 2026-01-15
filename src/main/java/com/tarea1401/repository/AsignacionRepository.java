package com.tarea1401.repository;

import com.tarea1401.model.Asignacion;
import com.tarea1401.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AsignacionRepository extends JpaRepository<Asignacion, Long> {

    boolean existsByConductorAndFechaFinIsNull(Conductor conductor);

    Optional<Asignacion> findByConductorAndFechaFinIsNull(Conductor conductor);
}
