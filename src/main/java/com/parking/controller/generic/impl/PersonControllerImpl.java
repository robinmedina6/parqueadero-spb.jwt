package com.parking.controller.generic.impl;


import com.parking.controller.PersonController;

import com.parking.controller.generic.generic.ControllerGenericThymeleafImpl;
import com.parking.modelo.generic.Person;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/person")
public class PersonControllerImpl extends ControllerGenericThymeleafImpl<Person> implements PersonController {


    public PersonControllerImpl(){
        setEntity();
    }


    @Override
    public void setEntity() {
        this.setNewEntity(new Person());
    }
}
