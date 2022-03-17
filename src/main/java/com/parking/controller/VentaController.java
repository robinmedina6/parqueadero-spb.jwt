package com.parking.controller;

import com.parking.modelo.Empleado;
import com.parking.modelo.Producto;
import com.parking.modelo.Venta;
import com.parking.service.EmpleadoService;
import com.parking.service.ProductoService;
import com.parking.service.VentaService;
import com.parking.utils.HelperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

@Controller
@RequestMapping("/venta")
public class VentaController {

    Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    VentaService ventaService;

    @Autowired
    ProductoService productoService;

    @Autowired
    EmpleadoService empleadoService;

    private List<Producto> listaProductos ;
    private List<Empleado> listaEmpleados ;

    public VentaController(){
    }

    @GetMapping("lista")
    public ModelAndView list(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("venta/lista");
        List<Venta> ventas = ventaService.listHoy();
        for (Venta venta: ventas) {

            log.info(venta.toString());

        }
        mv.addObject("ventas", ventas);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("nuevo")
    public ModelAndView nuevo(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("venta/nuevo");
        listaProductos = productoService.list();
        listaEmpleados = empleadoService.list();
        mv.addObject("productos", listaProductos);
        mv.addObject("empleados", listaEmpleados);
        return mv;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/guardar")
    public ModelAndView crear(@RequestParam String nombreCliente,
                              @RequestParam String cedulaCliente,
                              @RequestParam String telefonoCliente,
                              @RequestParam String marcaVehiculo,
                              @RequestParam String placaVehiculo,
                              @RequestParam String tipoVehiculo,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Calendar horaEntrada,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Calendar horaSalida,
                              @RequestParam Double valorVenta,
                              @RequestParam int id_empleado,
                              @RequestParam int id_producto
    ) throws ParseException {
        ModelAndView mv = new ModelAndView();

        Venta venta = Venta.builder().nombreCliente(nombreCliente)
                .cedulaCliente(cedulaCliente)
                .telefonoCliente(telefonoCliente)
                .marcaVehiculo(marcaVehiculo).placaVehiculo(placaVehiculo).tipoVehiculo(tipoVehiculo)
                .horaEntrada(horaEntrada)
                .empleado(empleadoService.getOne(id_empleado).get())
                .producto(productoService.getOne(id_producto).get()).build();
        ventaService.save(venta);

        log.info("se ingreso a actualizar:" + venta.toString());
        mv.setViewName("redirect:/venta/lista");
        return mv;
    }

    @GetMapping("/detalle/{id}")
    public ModelAndView detalle(@PathVariable("id") int id){
        if(!ventaService.existsById(id))
            return new ModelAndView("redirect:/venta/lista");
        Venta venta = ventaService.getOne(id).get();
        ModelAndView mv = new ModelAndView("venta/detalle");
        mv.addObject("venta", venta);
        mv.addObject("productos", listaProductos);
        mv.addObject("empleados", listaEmpleados);
        return mv;

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable("id") int id){
        if(!ventaService.existsById(id))
            return new ModelAndView("redirect:/venta/lista");
        Venta venta = ventaService.getOne(id).get();
        ModelAndView mv = new ModelAndView("venta/editar");
        mv.addObject("venta", venta);

        listaProductos = productoService.list();
        listaEmpleados = empleadoService.list();
        mv.addObject("productos", listaProductos);
        mv.addObject("empleados", listaEmpleados);
        return mv;

    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/actualizar")
    public ModelAndView actualizar(@RequestParam int id,
                                   @RequestParam String nombreCliente,
                                   @RequestParam String cedulaCliente,
                                   @RequestParam String telefonoCliente,
                                   @RequestParam String marcaVehiculo,
                                   @RequestParam String placaVehiculo,
                                   @RequestParam String tipoVehiculo,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Calendar horaEntrada,
                                   @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Calendar horaSalida,
                                   @RequestParam Double valorVenta,
                                   @RequestParam int id_empleado,
                                   @RequestParam int id_producto
    ) throws ParseException {
        if(!ventaService.existsById(id))
            return new ModelAndView("redirect:/venta/lista");
        ModelAndView mv = new ModelAndView();
        Venta venta = ventaService.getOne(id).get();


        venta.setNombreCliente(nombreCliente);
        venta.setCedulaCliente(cedulaCliente);
        venta.setTelefonoCliente(telefonoCliente);
        venta.setMarcaVehiculo(marcaVehiculo);
        venta.setPlacaVehiculo(placaVehiculo);
        venta.setTipoVehiculo(tipoVehiculo);
        venta.setHoraEntrada(horaEntrada);
        venta.setHoraSalida(horaSalida);
        venta.setEmpleado(empleadoService.getOne(id_empleado).get());
        venta.setProducto(productoService.getOne(id_producto).get());


        double precio = 0;
        if(venta.getProducto().getNombre().equals("parqueadero")) {
            precio = HelperUtils.restarCalendar(horaEntrada, horaSalida).getTimeInMillis() / 1000 / 60 / 60;
            if (tipoVehiculo.equals("camion")) {
                precio = precio * 5000;
            } else if (tipoVehiculo.equals("automovil")) {
                precio = precio * 3000;
            } else if (tipoVehiculo.equals("moto")) {
                precio = precio * 2000;
            }
        }else {
            precio= venta.getProducto().getPrecio();
        }

        venta.setValorVenta(precio);

        ventaService.save(venta);


        log.info("se ingreso a actualizar:" + venta.toString());
        return new ModelAndView("redirect:/venta/lista");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/borrar/{id}")
    public ModelAndView borrar(@PathVariable("id")int id){
        if(ventaService.existsById(id)){
            ventaService.delete(id);
            return new ModelAndView("redirect:/venta/lista");
        }
        return null;
    }


    @GetMapping("/pagina/{numeroPagina}/{campoOrden}/{sentidoOrden}")
    public ModelAndView listarVentas(@PathVariable("numeroPagina") Integer numeroPagina,
                                    @PathVariable("campoOrden") String campoOrden, @PathVariable("sentidoOrden") String sentidoOrden) {

        ModelAndView mav = new ModelAndView("venta/listaVentas");

        /*
         * Total de elementos por pagina
         * */
        int tamanioPagina = 15;

        Page<Venta> page = ventaService.obtenerCiudades(numeroPagina, tamanioPagina, campoOrden, sentidoOrden);

        List<Venta> listaVentas = page.getContent();

        mav.addObject("numeroPagina", numeroPagina);
        mav.addObject("totalPaginas", page.getTotalPages());
        mav.addObject("totalElementos", page.getTotalElements());
        mav.addObject("listaVentas", listaVentas);

        mav.addObject("campoOrden", campoOrden);
        mav.addObject("sentidoOrden", sentidoOrden);
        mav.addObject("tipoSentidoOrden", sentidoOrden.equals("asc") ? "desc" : "asc");

        log.info("Muestra vista listaVentas.html con resutados");

        return mav;

    }


}
