package com.parking.controller;

import com.parking.modelo.Venta;
import com.parking.service.VentaService;
import com.parking.utils.HelperUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/venta")
public class VentaController {

    @Autowired
    VentaService ventaService;

    @GetMapping("lista")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/venta/lista");
        List<Venta> ventas = ventaService.list();
        mv.addObject("ventas", ventas);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("nuevo")
    public String nuevo(){
        return "venta/nuevo";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ModelAndView crear(@RequestParam String nombreCliente,
                              @RequestParam String cedulaCliente,
                              @RequestParam String telefonoCliente,
                              @RequestParam String marcaVehiculo,
                              @RequestParam String placaVehiculo,
                              @RequestParam String tipoVehiculo,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Calendar horaEntrada
    ) throws ParseException {
        ModelAndView mv = new ModelAndView();
        if(StringUtils.isBlank(nombreCliente)){
            mv.setViewName("venta/nuevo");
            mv.addObject("error", "el nombre no puede estar vacío");
            return mv;
        }
        if(ventaService.existsByNombre(nombreCliente)){
            mv.setViewName("venta/nuevo");
            mv.addObject("error", "ese nombre ya existe");
            return mv;
        }
        Venta venta = Venta.builder().nombreCliente(nombreCliente)
                .cedulacliente(cedulaCliente)
                .telefonoCliente(telefonoCliente)
                .marcaVehiculo(marcaVehiculo).placaVehiculo(placaVehiculo).tipoVehiculo(tipoVehiculo)
                .horaEntrada(horaEntrada).build();
        ventaService.save(venta);
        mv.setViewName("redirect:/venta/lista");
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalle(@PathVariable("id") int id){
        if(!ventaService.existsById(id))
            return new ModelAndView("redirect:/venta/lista");
        Venta producto = ventaService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/venta/detalle");
        mv.addObject("venta", producto);
        return mv;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") int id){
        if(!ventaService.existsById(id))
            return new ModelAndView("redirect:/venta/lista");
        Venta venta = ventaService.getOne(id).get();
        ModelAndView mv = new ModelAndView("/venta/editar");
        mv.addObject("venta", venta);
        return mv;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam int id,@RequestParam String horaSalida) throws ParseException {
        if(!ventaService.existsById(id))
            return new ModelAndView("redirect:/producto/lista");
        ModelAndView mv = new ModelAndView();
        Venta venta = ventaService.getOne(id).get();
        double precio = HelperUtils.restarCalendar(venta.getHoraEntrada(),HelperUtils.convertStringToCalendar(horaSalida)).getTimeInMillis();
        if(precio <1 ){
            mv.setViewName("venta/editar");
            mv.addObject("error", "el precio debe ser mayor que cero");
            mv.addObject("producto", venta);
            return mv;
        }
        venta.setHoraSalida(HelperUtils.convertStringToCalendar(horaSalida));
        venta.setValorVenta(precio);
        ventaService.save(venta);
        return new ModelAndView("redirect:/venta/lista");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id")int id){
        if(ventaService.existsById(id)){
            ventaService.delete(id);
            return new ModelAndView("redirect:/producto/lista");
        }
        return null;
    }


}
