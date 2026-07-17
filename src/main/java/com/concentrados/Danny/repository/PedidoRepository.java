package com.concentrados.Danny.repository;

import com.concentrados.Danny.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Hace el llamado directo al procedimiento que creamos en Oracle Cloud
    @Procedure(procedureName = "sp_crear_pedido")
    Long crearPedido(
        @Param("p_id_usuario") Long idUsuario,
        @Param("p_id_horario") Long idHorario,
        @Param("p_id_zona") Long idZona,
        @Param("p_fecha_entrega") Date fechaEntrega,
        @Param("p_hora_entrega") String horaEntrega,
        @Param("p_id_direccion") Long direccion
    );
}