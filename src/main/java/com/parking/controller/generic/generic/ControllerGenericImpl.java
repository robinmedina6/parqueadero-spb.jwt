package com.parking.controller.generic.generic;


import com.parking.controller.generic.IControllerGeneric;
import com.parking.modelo.generic.base.BaseEntity;
import com.parking.service.generics.generic.IServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ResponseBody
public class ControllerGenericImpl<T extends BaseEntity> implements IControllerGeneric<T> {
	
	@Autowired
	private IServiceGeneric<T> genericService;

	@Override
	@PostMapping
	public ResponseEntity<Object> save(T entity) {
		try {
			return new ResponseEntity(genericService.save(entity),HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Error al guardar!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@GetMapping
	public ResponseEntity<T> findAll() {
		try {
			return new ResponseEntity(genericService.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Error al Buscar", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	@DeleteMapping("/{id}")	
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		try {
			genericService.delete(id);
			return new ResponseEntity("se elimino de manera satisfactoria!", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("error al eliminar!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
