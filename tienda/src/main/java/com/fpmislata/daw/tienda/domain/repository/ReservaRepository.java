package com.fpmislata.daw.tienda.domain.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.ReservaEntity;
import com.fpmislata.daw.tienda.enums.EstadoReserva;

public interface ReservaRepository {

    Optional<ReservaEntity> findById(long id);

    ReservaEntity save(ReservaEntity reservaEntity);

    void deleteById(long id);

    List<ReservaEntity> findByPeluqueriaAndFecha(long peluqueriaId, LocalDate fecha);

    List<ReservaEntity> findByCliente(long clienteId);

    List<ReservaEntity> findByClienteAndEstado(long clienteId, EstadoReserva estado);

    List<ReservaEntity> findByPeluqueria(long peluqueriaId);

    List<ReservaEntity> findByPeluqueriaAndEstado(long peluqueriaId, EstadoReserva estado);

    List<ReservaEntity> findByPeluqueriaAndDia(long peluqueriaId, int diaSemana);

    Optional<ReservaEntity> findByIdAndClienteId(long reservaId, long clienteId);

    Optional<ReservaEntity> findByIdAndPeluqueriaId(long reservaId, long peluqueriaId);
}
