package com.parking.modelo.generic;

import com.parking.modelo.generic.base.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "person")
public class Person extends BaseEntity {
	
	@Column(nullable = false, length = 30)
	private String nombre;
	@Column( nullable = true, length = 10)
	private String apellido;
	@Column( nullable = false, length = 50)
	private String email;


}
