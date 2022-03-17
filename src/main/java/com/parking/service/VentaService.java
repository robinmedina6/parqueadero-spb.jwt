package com.parking.service;

import com.parking.modelo.Venta;
import com.parking.repository.VentaRepository;
import com.parking.utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public List<Venta> listHoy(){
        return ventaRepository.findByHoraEntradaHoy("%"+HelperUtils.fechaHoyMysql()+"%").get();
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


    public Page<Venta> obtenerCiudades(int numeroPagina, int tamanioPagina, String campoOrden, String sentidoOrden) {

        Pageable pageable = PageRequest.of(numeroPagina - 1, tamanioPagina,
                sentidoOrden.equals("asc") ? Sort.by(campoOrden).ascending() : Sort.by(campoOrden).descending());

        return ventaRepository.findAll(pageable);
    }
}
