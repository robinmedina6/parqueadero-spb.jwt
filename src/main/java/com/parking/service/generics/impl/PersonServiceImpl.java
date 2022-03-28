package com.parking.service.generics.impl;

import com.parking.modelo.generic.Person;
import com.parking.service.generics.IPersonService;
import com.parking.service.generics.genericImpl.ServiceGenericImpl;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends ServiceGenericImpl<Person> implements IPersonService {

}
