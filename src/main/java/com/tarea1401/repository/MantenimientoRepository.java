package com.tarea1401.repository;

import com.tarea1401.model.Mantenimiento;
import com.tarea1401.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {

    List<Mantenimiento> findByVehiculoOrderByFechaDesc(Vehiculo vehiculo);

    @Query("SELECT m FROM Mantenimiento m WHERE m.vehiculo.id = :vehiculoId ORDER BY m.fecha DESC")
    List<Mantenimiento> findUltimoMantenimientoByVehiculoId(@Param("vehiculoId") Long vehiculoId);

}
