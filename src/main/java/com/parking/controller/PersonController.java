package com.parking.controller;


import com.parking.controller.generic.IControllerGenericThymeleaf;
import com.parking.modelo.generic.Person;

public interface PersonController extends IControllerGenericThymeleaf<Person> {
    public void setEntity();
}
