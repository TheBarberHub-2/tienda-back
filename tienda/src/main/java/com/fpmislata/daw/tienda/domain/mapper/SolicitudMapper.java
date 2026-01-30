package com.fpmislata.daw.tienda.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.domain.model.Solicitud;
import com.fpmislata.daw.tienda.domain.model.SolicitudPeluqueria;
import com.fpmislata.daw.tienda.domain.model.SolicitudProducto;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudPeluqueriaEntity;
import com.fpmislata.daw.tienda.domain.repository.entity.SolicitudProductoEntity;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudDto;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudPeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.SolicitudProductoDto;

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

        List<SolicitudPeluqueria> solicitudesPeluqueria = new ArrayList<>();
        if (entity.solicitudesPeluqueria() != null && !entity.solicitudesPeluqueria().isEmpty()) {
            solicitudesPeluqueria = entity.solicitudesPeluqueria().stream()
                    .map(SolicitudPeluqueriaMapper.getInstance()::fromEntityToModel)
                    .toList();
        }

        List<SolicitudProducto> solicitudesProducto = new ArrayList<>();
        if (entity.solicitudesProducto() != null && !entity.solicitudesProducto().isEmpty()) {
            solicitudesProducto = entity.solicitudesProducto().stream()
                    .map(SolicitudProductoMapper.getInstance()::fromEntityToModel)
                    .toList();
        }

        return new Solicitud(
                entity.id(),
                UsuarioMapper.getInstance().fromEntityToModel(entity.usuario()),
                entity.tipo(),
                entity.estado(),
                entity.fecha(),
                solicitudesPeluqueria,
                solicitudesProducto);
    }

    // MODEL → ENTITY
    public SolicitudEntity fromModelToEntity(Solicitud model) {
        if (model == null) {
            return null;
        }

        List<SolicitudPeluqueriaEntity> solicitudesPeluqueria = new ArrayList<>();
        if (model.getSolicitudesPeluqueria() != null && !model.getSolicitudesPeluqueria().isEmpty()) {
            solicitudesPeluqueria = model.getSolicitudesPeluqueria().stream()
                    .map(SolicitudPeluqueriaMapper.getInstance()::fromModelToEntity)
                    .toList();
        }

        List<SolicitudProductoEntity> solicitudesProducto = new ArrayList<>();
        if (model.getSolicitudesProducto() != null && !model.getSolicitudesProducto().isEmpty()) {
            solicitudesProducto = model.getSolicitudesProducto().stream()
                    .map(SolicitudProductoMapper.getInstance()::fromModelToEntity)
                    .toList();
        }

        return new SolicitudEntity(
                model.getId(),
                UsuarioMapper.getInstance().fromModelToEntity(model.getUsuario()),
                model.getTipo(),
                model.getEstado(),
                model.getFecha(),
                solicitudesPeluqueria,
                solicitudesProducto);
    }

    // DTO → MODEL
    public Solicitud fromDtoToModel(SolicitudDto dto) {
        if (dto == null) {
            return null;
        }

        List<SolicitudPeluqueria> solicitudesPeluqueria = new ArrayList<>();
        if (dto.solicitudesPeluqueria() != null && !dto.solicitudesPeluqueria().isEmpty()) {
            solicitudesPeluqueria = dto.solicitudesPeluqueria().stream()
                    .map(SolicitudPeluqueriaMapper.getInstance()::fromDtoToModel)
                    .toList();
        }

        List<SolicitudProducto> solicitudesProducto = new ArrayList<>();
        if (dto.solicitudesProducto() != null && !dto.solicitudesProducto().isEmpty()) {
            solicitudesProducto = dto.solicitudesProducto().stream()
                    .map(SolicitudProductoMapper.getInstance()::fromDtoToModel)
                    .toList();
        }

        return new Solicitud(
                dto.id(),
                UsuarioMapper.getInstance().fromDtoToModel(dto.usuario()),
                dto.tipo(),
                dto.estado(),
                dto.fecha(),
                solicitudesPeluqueria,
                solicitudesProducto);
    }

    // MODEL → DTO
    public SolicitudDto fromModelToDto(Solicitud model) {
        if (model == null) {
            return null;
        }

        List<SolicitudPeluqueriaDto> solicitudesPeluqueria = new ArrayList<>();
        if (model.getSolicitudesPeluqueria() != null && !model.getSolicitudesPeluqueria().isEmpty()) {
            solicitudesPeluqueria = model.getSolicitudesPeluqueria().stream()
                    .map(SolicitudPeluqueriaMapper.getInstance()::fromModelToDto)
                    .toList();
        }

        List<SolicitudProductoDto> solicitudesProducto = new ArrayList<>();
        if (model.getSolicitudesProducto() != null && !model.getSolicitudesProducto().isEmpty()) {
            solicitudesProducto = model.getSolicitudesProducto().stream()
                    .map(SolicitudProductoMapper.getInstance()::fromModelToDto)
                    .toList();
        }

        return new SolicitudDto(
                model.getId(),
                UsuarioMapper.getInstance().fromModelToDto(model.getUsuario()),
                model.getTipo(),
                model.getEstado(),
                model.getFecha(),
                solicitudesPeluqueria,
                solicitudesProducto);
    }
}