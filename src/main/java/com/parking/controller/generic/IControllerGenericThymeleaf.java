package com.parking.controller.generic;

import com.parking.controller.generic.generic.GenericMessage;
import com.parking.modelo.generic.base.BaseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

public interface IControllerGenericThymeleaf<T extends BaseEntity> {

	ModelAndView nuevo();

	ResponseEntity<GenericMessage> save(@RequestBody T entity);

	ModelAndView lista(@RequestBody T entity);

	ResponseEntity<T> findAll();

	ResponseEntity<String> delete(@PathVariable Long id);

}
