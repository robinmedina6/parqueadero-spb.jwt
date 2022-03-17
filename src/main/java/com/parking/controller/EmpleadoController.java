package com.parking.controller;

import com.parking.modelo.Empleado;
import com.parking.service.EmpleadoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;
    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("lista")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("empleado/lista");
        List<Empleado> empleados = empleadoService.list();
        mv.addObject("empleados", empleados);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("nuevo")
    public String nuevo(){
        return "empleado/nuevo";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ModelAndView crear(@RequestParam String nombre,
                              @RequestParam String apellido,
                              @RequestParam String direccion,
                              @RequestParam String telefono,
                              @RequestParam String cedula

    ){
        ModelAndView mv = new ModelAndView();
        if(StringUtils.isBlank(nombre)){
            mv.setViewName("empleado/nuevo");
            mv.addObject("error", "el nombre no puede estar vacío");
            return mv;
        }
        Empleado empleado = new Empleado(0,nombre, apellido,direccion,telefono,cedula);
        log.info("se ingresa a insertar empleado"+ empleado.toString());
        empleadoService.save(empleado);
        mv.setViewName("redirect:/empleado/lista");
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalle(@PathVariable("id") int id){
        if(!empleadoService.existsById(id))
            return new ModelAndView("redirect:/empleado/lista");
        Empleado empleado = empleadoService.getOne(id).get();
        ModelAndView mv = new ModelAndView("empleado/detalle");
        mv.addObject("empleado", empleado);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") int id){
        if(!empleadoService.existsById(id))
            return new ModelAndView("redirect:/empleado/lista");
        Empleado empleado = empleadoService.getOne(id).get();
        ModelAndView mv = new ModelAndView("empleado/editar");
        mv.addObject("empleado", empleado);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam int id,
                                   @RequestParam String nombre,
                                   @RequestParam String apellido,
                                   @RequestParam String direccion,
                                   @RequestParam String telefono,
                                   @RequestParam String cedula
                                   ){
        if(!empleadoService.existsById(id))
            return new ModelAndView("redirect:/empleado/lista");
        ModelAndView mv = new ModelAndView();
        Empleado empleado = empleadoService.getOne(id).get();

        empleado.setNombre(nombre);
        empleado.setApellido(apellido);
        empleado.setDireccion(direccion);
        empleado.setTelefono(telefono);
        empleado.setCedula(cedula);

        empleadoService.save(empleado);
        log.info("se ingresa a editar empleado"+ empleado.toString());
        return new ModelAndView("redirect:/empleado/lista");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id")int id){
        if(empleadoService.existsById(id)){
            log.info("se ingresa a eliminar empleado"+ id);
            empleadoService.delete(id);
            return new ModelAndView("redirect:/empleado/lista");
        }
        return null;
    }


}
