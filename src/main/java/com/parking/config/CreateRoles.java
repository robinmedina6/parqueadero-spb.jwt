package com.parking.config;

import com.parking.enums.RolNombre;
import com.parking.modelo.Rol;
import com.parking.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        crearRoles();
    }
    public void crearRoles(){
        if(!rolService.existsByRolNombre(RolNombre.ROLE_ADMIN)) {
            Rol rolAdmin = new Rol(RolNombre.ROLE_ADMIN);
            rolService.save(rolAdmin);
        }
        if(!rolService.existsByRolNombre(RolNombre.ROLE_USER)) {
            Rol rolUser = new Rol(RolNombre.ROLE_USER);
            rolService.save(rolUser);
        }
    }
}
