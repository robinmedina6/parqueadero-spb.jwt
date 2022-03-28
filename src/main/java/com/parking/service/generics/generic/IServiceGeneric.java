package com.parking.service.generics.generic;



import com.parking.modelo.generic.base.BaseEntity;

import java.lang.reflect.Field;
import java.util.List;

public interface IServiceGeneric<T extends BaseEntity>  {

	T findById(Long id)throws Exception;
	List<T> findAll() throws Exception;
	T save(T entity) throws Exception;
	void delete(Long id) throws Exception;
	
}
