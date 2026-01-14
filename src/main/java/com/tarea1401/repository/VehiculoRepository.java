package com.tarea1401.repository;

import com.tarea1401.dto.VehiculoConUltimoMantenimiento;
import com.tarea1401.model.Estado;
import com.tarea1401.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    List<Vehiculo> findByEstado(Estado estado);

}
