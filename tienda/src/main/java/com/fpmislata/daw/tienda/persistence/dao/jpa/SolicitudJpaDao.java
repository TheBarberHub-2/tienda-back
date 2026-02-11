package com.fpmislata.daw.tienda.persistence.dao.jpa;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudJpaEntity;

public interface SolicitudJpaDao {

    Optional<SolicitudJpaEntity> findById(long id);

    SolicitudJpaEntity insert(SolicitudJpaEntity entity);

    SolicitudJpaEntity update(SolicitudJpaEntity entity);

    List<SolicitudJpaEntity> findByUsuario(long usuarioId);

    List<SolicitudJpaEntity> findByTipoAndEstado(TipoSolicitud tipo, EstadoSolicitud estado);

    List<SolicitudJpaEntity> findPendientes();

    List<SolicitudJpaEntity> findAprobadas();

    List<SolicitudJpaEntity> findAll(int page, int size);

    long count();
}