package com.parking.repository;

import com.parking.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    Optional<Venta> findByNombreCliente(String nombre);
    boolean existsByNombreCliente(String nombre);

    @Query(value = "SELECT * FROM venta WHERE hora_entrada LIKE(?1)",
            nativeQuery = true)
    Optional<List<Venta>> findByHoraEntradaHoy(String fecha);

    @Query(value = "SELECT * FROM venta WHERE placa_vehiculo LIKE(?1)",
            nativeQuery = true)
    Optional<List<Venta>> findByPlacaLike(String placa);

}
