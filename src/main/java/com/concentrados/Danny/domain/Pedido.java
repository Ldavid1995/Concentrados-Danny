package com.concentrados.Danny.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "PEDIDO")
public class Pedido implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PEDIDO")
    private Long idPedido;
    
    @Column(name = "ID_USUARIO")
    private Long idUsuario;
    
    @Column(name = "ID_HORARIO")
    private Long idHorario;
    
    @Column(name = "ID_ZONA")
    private Long idZona;
    
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_ENTREGA")
    private Date fechaEntrega;
    
    @Column(name = "HORA_ENTREGA")
    private String horaEntrega;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "ESTADO_PEDIDO")
    private String estadoPedido;
}
