package com.parking.service;

import com.parking.modelo.Empleado;
import com.parking.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmpleadoService {

    @Autowired
    EmpleadoRepository empleadoRepository;

    public List<Empleado> list(){
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> getOne(int id){
        return empleadoRepository.findById(id);
    }

    public Optional<Empleado> getByNombre(String nombre){
        return empleadoRepository.findByNombre(nombre);
    }

    public void  save(Empleado producto){
        empleadoRepository.save(producto);
    }

    public void delete(int id){
        empleadoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return empleadoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return empleadoRepository.existsByNombre(nombre);
    }
}
