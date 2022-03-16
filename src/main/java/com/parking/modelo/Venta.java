package com.parking.modelo;

import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Calendar;

@Entity
@AllArgsConstructor @NoArgsConstructor @Builder @Getter @Setter
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Column(unique = false)
    private String nombreCliente;
    private String cedulacliente;
    private String telefonoCliente;
    private String marcaVehiculo;
    private String placaVehiculo;
    private String tipoVehiculo;
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaEntrada;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaSalida;
    @Column(nullable = true)
    private Double valorVenta;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
