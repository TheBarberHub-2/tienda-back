package com.fpmislata.daw.tienda.persistence.repository.mapper;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudJpaEntity;

public class SolicitudMapper {

    private static SolicitudMapper INSTANCE;

    private SolicitudMapper() {
    }

    public static SolicitudMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SolicitudMapper();
        }
        return INSTANCE;
    }

    public SolicitudEntity fromJpaToEntity(SolicitudJpaEntity solicitudJpa) {
        if (solicitudJpa == null) {
            return null;
        }

        return new SolicitudEntity(
                solicitudJpa.getId(),
                UsuarioMapper.getInstance().fromJpaToEntity(solicitudJpa.getUsuario()),
                solicitudJpa.getTipo(),
                solicitudJpa.getEstado(),
                solicitudJpa.getFecha());
    }

    public SolicitudJpaEntity fromEntityToJpa(SolicitudEntity solicitudEntity) {
        if (solicitudEntity == null) {
            return null;
        }

        return new SolicitudJpaEntity(
                solicitudEntity.id(),
                UsuarioMapper.getInstance().fromEntityToJpa(solicitudEntity.usuario()),
                solicitudEntity.tipo(),
                solicitudEntity.estado(),
                solicitudEntity.fecha());
    }
}