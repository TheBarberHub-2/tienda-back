package com.fpmislata.daw.tienda.domain.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;

public interface SolicitudRepository {

    Optional<SolicitudEntity> findById(long id);

    List<SolicitudEntity> findByUsuario(long usuarioId);

    List<SolicitudEntity> findByTipoAndEstado(TipoSolicitud tipo, EstadoSolicitud estado);

    List<SolicitudEntity> findPendientes();

    List<SolicitudEntity> findAprobadas();

    SolicitudEntity save(SolicitudEntity solicitud);

    List<SolicitudEntity> findAll(int page, int size);

    long count();
}