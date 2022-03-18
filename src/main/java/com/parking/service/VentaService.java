package com.parking.service;

import com.parking.modelo.Venta;
import com.parking.repository.VentaRepository;
import com.parking.utils.HelperUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Repository
public class VentaService {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    @Autowired
    VentaRepository ventaRepository;

    public List<Venta> list(){
        return ventaRepository.findAll();
    }

    public List<Venta> listHoy(){
        return ventaRepository.findByHoraEntradaHoy("%"+HelperUtils.fechaHoyMysql()+"%").get();
    }

    public List<Venta> listByFields(Venta venta){

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venta> cq= cb.createQuery(Venta.class);

        Root<Venta> ventaRoot = cq.from(Venta.class);
        List<Predicate> predicates = new ArrayList<>();

        if(null!=venta.getNombreCliente() && !venta.getNombreCliente().trim().isEmpty()){
            predicates.add(cb.like(ventaRoot.get("nombreCliente").as(String.class),"%"+venta.getNombreCliente().toString()+"%"));
        }

        if(null!=venta.getCedulaCliente() && !venta.getCedulaCliente().trim().isEmpty()){
            predicates.add(cb.like(ventaRoot.get("cedulaCliente").as(String.class),"%"+venta.getCedulaCliente().toString()+"%"));
        }

        if(null!=venta.getMarcaVehiculo() && !venta.getMarcaVehiculo().trim().isEmpty()){
            predicates.add(cb.like(ventaRoot.get("marcaVehiculo").as(String.class),"%"+venta.getMarcaVehiculo().toString()+"%"));
        }

        if(null!=venta.getPlacaVehiculo() && !venta.getPlacaVehiculo().trim().isEmpty()){
            predicates.add(cb.like(ventaRoot.get("placaVehiculo").as(String.class),"%"+venta.getPlacaVehiculo().toString()+"%"));
        }

        if(null!=venta.getTipoVehiculo() && !venta.getTipoVehiculo().trim().isEmpty()){
            predicates.add(cb.like(ventaRoot.get("tipoVehiculo").as(String.class),"%"+venta.getPlacaVehiculo().toString()+"%"));
        }
        log.info("filtrando"+ venta.getHoraEntrada());
        if(null!=venta.getHoraEntrada()){
            predicates.add(cb.like(ventaRoot.get("horaEntrada").as(String.class),"%"+HelperUtils.convertCalendarToStringMysql(venta.getHoraEntrada())+"%"));
        }
        if(null!=venta.getHoraSalida()){
            predicates.add(cb.like(ventaRoot.get("horaSalida").as(String.class),"%"+HelperUtils.convertCalendarToStringMysql(venta.getHoraSalida())+"%"));
        }

        if(null!=venta.getEmpleado()){
            predicates.add(cb.equal(ventaRoot.get("empleado") ,venta.getEmpleado()));
        }
        if(null!=venta.getProducto()){
            predicates.add(cb.equal(ventaRoot.get("producto") ,venta.getProducto()));
        }



        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();


/*
        if(venta.getPlacaVehiculo()!=null && !venta.getPlacaVehiculo().trim().isEmpty()){
            Optional<List<Venta>> olv=ventaRepository.findByPlacaLike("%"+venta.getPlacaVehiculo()+"%");
            if(!olv.isEmpty()) {
                return olv.get();
            }else{
                return null;
            }
        } else {return  null;}

 */
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
