package com.fpmislata.daw.tienda.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.fpmislata.daw.tienda.domain.repository.SolicitudRepository;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.enums.EstadoSolicitud;
import com.fpmislata.daw.tienda.enums.TipoSolicitud;
import com.fpmislata.daw.tienda.persistence.dao.jpa.SolicitudJpaDao;
import com.fpmislata.daw.tienda.persistence.repository.mapper.SolicitudMapper;

public class SolicitudRepositoryImpl implements SolicitudRepository {

    private final SolicitudJpaDao solicitudJpaDao;

    public SolicitudRepositoryImpl(SolicitudJpaDao solicitudJpaDao) {
        this.solicitudJpaDao = solicitudJpaDao;
    }

    @Override
    public Optional<SolicitudEntity> findById(long id) {
        return solicitudJpaDao.findById(id)
                .map(SolicitudMapper.getInstance()::fromJpaToEntity);
    }

    @Override
    public List<SolicitudEntity> findByUsuario(long usuarioId) {
        return solicitudJpaDao.findByUsuario(usuarioId).stream()
                .map(SolicitudMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<SolicitudEntity> findByTipoAndEstado(TipoSolicitud tipo, EstadoSolicitud estado) {
        return solicitudJpaDao.findByTipoAndEstado(tipo, estado).stream()
                .map(SolicitudMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<SolicitudEntity> findPendientes() {
        return solicitudJpaDao.findPendientes().stream()
                .map(SolicitudMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public List<SolicitudEntity> findAprobadas() {
        return solicitudJpaDao.findAprobadas().stream()
                .map(SolicitudMapper.getInstance()::fromJpaToEntity)
                .toList();
    }

    @Override
    public SolicitudEntity save(SolicitudEntity solicitud) {
        var jpa = SolicitudMapper.getInstance().fromEntityToJpa(solicitud);

        if (solicitud.id() == null) {
            return SolicitudMapper.getInstance().fromJpaToEntity(solicitudJpaDao.insert(jpa));
        }

        return SolicitudMapper.getInstance().fromJpaToEntity(solicitudJpaDao.update(jpa));
    }

    @Override
    public long count() {
        return solicitudJpaDao.count();
    }
}