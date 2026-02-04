package com.fpmislata.daw.tienda.domain.mapper;

import com.fpmislata.daw.tienda.domain.model.Solicitud;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;

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

    // ENTITY → MODEL
    public Solicitud fromEntityToModel(SolicitudEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Solicitud(
                entity.id(),
                UsuarioMapper.getInstance().fromEntityToModel(entity.usuario()),
                entity.tipo(),
                entity.estado(),
                entity.fecha());
    }

    // MODEL → ENTITY
    public SolicitudEntity fromModelToEntity(Solicitud model) {
        if (model == null) {
            return null;
        }

        return new SolicitudEntity(
                model.getId(),
                UsuarioMapper.getInstance().fromModelToEntity(model.getUsuario()),
                model.getTipo(),
                model.getEstado(),
                model.getFecha());
    }

    // DTO → MODEL
    public Solicitud fromDtoToModel(SolicitudDto dto) {
        if (dto == null) {
            return null;
        }

        return new Solicitud(
                dto.id(),
                UsuarioMapper.getInstance().fromDtoToModel(dto.usuario()),
                dto.tipo(),
                dto.estado(),
                dto.fecha());
    }

    // MODEL → DTO
    public SolicitudDto fromModelToDto(Solicitud model) {
        if (model == null) {
            return null;
        }

        return new SolicitudDto(
                model.getId(),
                UsuarioMapper.getInstance().fromModelToDto(model.getUsuario()),
                model.getTipo(),
                model.getEstado(),
                model.getFecha());
    }
}