package com.parking.modelo;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombre;
    private String apellido;
    private String direccion;
    private String telefono;
    private String cedula;

}
