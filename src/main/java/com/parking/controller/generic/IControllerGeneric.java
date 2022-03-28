package com.parking.controller.generic;

import com.parking.modelo.generic.base.BaseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IControllerGeneric<T extends BaseEntity> {
	
	ResponseEntity<Object> save(@RequestBody T entity);
	
	ResponseEntity<T> findAll();
	
	ResponseEntity<String> delete(@PathVariable Long id);

}
