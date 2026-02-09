package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.enums.EstadoReserva;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.ReservaJpaEntity;

public interface ReservaJpaDao extends GenericJpaDao<ReservaJpaEntity> {

    List<ReservaJpaEntity> findByPeluqueriaAndFecha(long peluqueriaId, LocalDate fecha);

    List<ReservaJpaEntity> findByCliente(long clienteId);

    List<ReservaJpaEntity> findByClienteAndEstado(long clienteId, EstadoReserva estado);

    List<ReservaJpaEntity> findByPeluqueria(long peluqueriaID);

    List<ReservaJpaEntity> findByPeluqueriaAndEstado(long peluqueriaId, EstadoReserva estado);

    Optional<ReservaJpaEntity> findByIdAndClienteId(long reservaId, long clienteId);

    Optional<ReservaJpaEntity> findByIdAndPeluqueriaId(long reservaId, long peluqueriaId);
}
