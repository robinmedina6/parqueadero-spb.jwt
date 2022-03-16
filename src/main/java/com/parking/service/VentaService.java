package com.parking.service;

import com.parking.modelo.Venta;
import com.parking.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService {

    @Autowired
    VentaRepository ventaRepository;

    public List<Venta> list(){
        return ventaRepository.findAll();
    }

    public Optional<Venta> getOne(int id){
        return ventaRepository.findById(id);
    }

    public Optional<Venta> getByNombre(String nombre){
        return ventaRepository.findByNombreCliente(nombre);
    }

    public void  save(Venta venta){
        ventaRepository.save(venta);
    }

    public void delete(int id){
        ventaRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return ventaRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return ventaRepository.existsByNombreCliente(nombre);
    }
}
