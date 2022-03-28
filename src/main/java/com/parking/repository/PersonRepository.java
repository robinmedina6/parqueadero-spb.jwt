package com.parking.repository;

import com.parking.modelo.generic.Person;
import com.parking.repository.generic.IGenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends IGenericRepository<Person> {

}
