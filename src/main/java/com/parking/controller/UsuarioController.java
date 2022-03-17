package com.parking.controller;

import com.parking.enums.RolNombre;
import com.parking.modelo.Usuario;
import com.parking.modelo.Rol;
import com.parking.service.RolService;
import com.parking.service.UsuarioService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @PostMapping("/registrar")
    public ModelAndView registrar(String nombreUsuario, String password){
        ModelAndView mv = new ModelAndView();
        if(StringUtils.isBlank(nombreUsuario)){
            mv.setViewName("registro");
            mv.addObject("error", "el nombre no puede estar vacío");
            return mv;
        }
        if(StringUtils.isBlank(password)){
            mv.setViewName("registro");
            mv.addObject("error", "la contraseña no puede estar vacía");
            return mv;
        }
        if(usuarioService.existsByNombreusuario(nombreUsuario)){
            mv.setViewName("registro");
            mv.addObject("error", "ese nombre de usuario ya existe");
            return mv;
        }
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(nombreUsuario);
        usuario.setPassword(passwordEncoder.encode(password));
        Rol rolUser = rolService.getByRolNombre(RolNombre.ROLE_USER).get();
        Set<Rol> roles = new HashSet<>();
        roles.add(rolUser);
        usuario.setRoles(roles);
        usuarioService.save(usuario);
        mv.setViewName("login");
        mv.addObject("registroOK", "Cuenta creada, " + usuario.getNombreUsuario() + ", ya puedes iniciar sesión");
        return mv;
    }









    @GetMapping("lista")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("producto/lista");
        List<Usuario> usuarios = usuarioService.lista();
        mv.addObject("productos", usuarios);
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalle(@PathVariable("id") int id){
        if(!usuarioService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        Usuario usuario = usuarioService.getById(id).get();
        ModelAndView mv = new ModelAndView("producto/detalle");
        mv.addObject("producto", usuario);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") int id){
        if(!usuarioService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        Usuario usuario = usuarioService.getById(id).get();
        ModelAndView mv = new ModelAndView("producto/editar");
        mv.addObject("usuario", usuario);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam int id, @RequestParam String nombre, @RequestParam float precio){
        if(!usuarioService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        ModelAndView mv = new ModelAndView();
        Usuario usuario = usuarioService.getById(id).get();
        if(StringUtils.isBlank(nombre)){
            mv.setViewName("producto/editar");
            mv.addObject("producto", usuario);
            mv.addObject("error", "el nombre no puede estar vacío");
            return mv;
        }
        if(precio <1 ){
            mv.setViewName("producto/editar");
            mv.addObject("error", "el precio debe ser mayor que cero");
            mv.addObject("producto", usuario);
            return mv;
        }
        if(usuarioService.existsByNombreusuario(nombre) && usuarioService.getByNombreUsuario(nombre).get().getId() != id){
            mv.setViewName("producto/editar");
            mv.addObject("error", "ese nombre ya existe");
            mv.addObject("producto", usuario);
            return mv;
        }

        usuario.setNombreUsuario(nombre);
        HashSet<Rol> rol= new  HashSet<Rol>();
        rol.add(new Rol(RolNombre.ROLE_USER));
        usuario.setRoles(rol);
        usuarioService.save(usuario);
        return new ModelAndView("redirect:/producto/lista");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id")int id){
        if(usuarioService.existsById(id)){
            usuarioService.delete(id);
            return new ModelAndView("redirect:/producto/lista");
        }
        return null;
    }


}
