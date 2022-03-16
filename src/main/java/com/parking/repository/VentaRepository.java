package com.parking.repository;

import com.parking.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    Optional<Venta> findByNombreCliente(String nombre);
    boolean existsByNombreCliente(String nombre);
}
