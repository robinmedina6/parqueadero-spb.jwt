package com.parking.config;

import com.parking.enums.RolNombre;
import com.parking.modelo.Rol;
import com.parking.modelo.Usuario;
import com.parking.service.RolService;
import com.parking.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreateAdmin implements CommandLineRunner {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    CreateRoles createRoles;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolService rolService;

    @Override
    public void run(String... args) throws Exception {
        createRoles.crearRoles();
        if(!usuarioService.existsByNombreusuario("admin")) {
            Usuario usuario = new Usuario();
            String passwordEncoded = passwordEncoder.encode("admin");
            usuario.setNombreUsuario("admin");
            usuario.setPassword(passwordEncoded);
            Rol rolAdmin = rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get();
            Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
            Set<Rol> roles = new HashSet<>();
            roles.add(rolAdmin);
            roles.add(rolUser);
            usuario.setRoles(roles);
            usuarioService.save(usuario);
        }
    }
}
