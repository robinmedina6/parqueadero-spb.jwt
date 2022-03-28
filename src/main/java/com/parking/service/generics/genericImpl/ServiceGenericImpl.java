package com.parking.service.generics.genericImpl;

import com.parking.modelo.generic.Person;
import com.parking.modelo.generic.base.BaseEntity;
import com.parking.repository.generic.IGenericRepository;
import com.parking.service.generics.generic.IServiceGeneric;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class ServiceGenericImpl<T extends BaseEntity> implements IServiceGeneric<T> {
	
	@Autowired
	protected IGenericRepository<T> genericRepository;


	@Override
	public T findById(Long id) throws Exception {
		return genericRepository.findById(id).get();
	}

	@Override
	public List<T> findAll() throws Exception {
		try {
			return genericRepository.findAll(); 
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public T save(T entity) throws Exception {
		try {
			return genericRepository.save(entity);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void delete(Long id) throws Exception {
		try {
			genericRepository.deleteById(id);
		} catch (Exception e) {
			throw e;
		}
	}

}
