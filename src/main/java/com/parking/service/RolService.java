package com.parking.service;

import com.parking.enums.RolNombre;
import com.parking.modelo.Rol;
import com.parking.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public void save(Rol rol){
        rolRepository.save(rol);
    }

    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return rolRepository.findByRolNombre(rolNombre);
    }

    public boolean existsByRolNombre(RolNombre rolNombre){
        return rolRepository.existsByRolNombre(rolNombre);
    }
}
