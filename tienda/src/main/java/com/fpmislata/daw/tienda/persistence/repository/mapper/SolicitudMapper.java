package com.fpmislata.daw.tienda.persistence.repository.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudPeluqueriaJpaEntity;
import com.fpmislata.daw.tienda.persistence.dao.jpa.entity.SolicitudProductoJpaEntity;

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

        List<SolicitudPeluqueriaEntity> solicitudesPeluqueria = new ArrayList<>();
        if (solicitudJpa.getSolicitudesPeluqueria() != null && !solicitudJpa.getSolicitudesPeluqueria().isEmpty()) {
            solicitudesPeluqueria = solicitudJpa.getSolicitudesPeluqueria().stream()
                    .map(SolicitudPeluqueriaMapper.getInstance()::fromJpaToEntity)
                    .toList();
        }

        List<SolicitudProductoEntity> solicitudesProducto = new ArrayList<>();
        if (solicitudJpa.getSolicitudesProducto() != null && !solicitudJpa.getSolicitudesProducto().isEmpty()) {
            solicitudesProducto = solicitudJpa.getSolicitudesProducto().stream()
                    .map(SolicitudProductoMapper.getInstance()::fromJpaToEntity)
                    .toList();
        }

        return new SolicitudEntity(
                solicitudJpa.getId(),
                UsuarioMapper.getInstance().fromJpaToEntity(solicitudJpa.getUsuario()),
                solicitudJpa.getTipo(),
                solicitudJpa.getEstado(),
                solicitudJpa.getFecha(),
                solicitudesPeluqueria,
                solicitudesProducto);
    }

    public SolicitudJpaEntity fromEntityToJpa(SolicitudEntity solicitudEntity) {
        if (solicitudEntity == null) {
            return null;
        }

        List<SolicitudPeluqueriaJpaEntity> solicitudesPeluqueria = new ArrayList<>();
        if (solicitudEntity.solicitudesPeluqueria() != null && !solicitudEntity.solicitudesPeluqueria().isEmpty()) {
            solicitudesPeluqueria = solicitudEntity.solicitudesPeluqueria().stream()
                    .map(SolicitudPeluqueriaMapper.getInstance()::fromEntityToJpa)
                    .toList();
        }

        List<SolicitudProductoJpaEntity> solicitudesProducto = new ArrayList<>();
        if (solicitudEntity.solicitudesProducto() != null && !solicitudEntity.solicitudesProducto().isEmpty()) {
            solicitudesProducto = solicitudEntity.solicitudesProducto().stream()
                    .map(SolicitudProductoMapper.getInstance()::fromEntityToJpa)
                    .toList();
        }

        return new SolicitudJpaEntity(
                solicitudEntity.id(),
                UsuarioMapper.getInstance().fromEntityToJpa(solicitudEntity.usuario()),
                solicitudEntity.tipo(),
                solicitudEntity.estado(),
                solicitudEntity.fecha(),
                solicitudesPeluqueria,
                solicitudesProducto);
    }
}