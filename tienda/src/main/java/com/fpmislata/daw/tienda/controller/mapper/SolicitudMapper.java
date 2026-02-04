package com.fpmislata.daw.tienda.controller.mapper;

import com.fpmislata.daw.tienda.controller.webModel.request.SolicitudPeluqueriaRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.SolicitudProductoRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.SolicitudDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.SolicitudPeluqueriaDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.SolicitudProductoDetailResponse;
import com.fpmislata.daw.tienda.domain.service.dto.CategoriaDto;
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

    public SolicitudPeluqueriaDetailResponse fromPeluqueriaToResponse(SolicitudPeluqueriaDto solicitudPeluqueriaDto) {
        if (solicitudPeluqueriaDto == null) {
            return null;
        }

        return new SolicitudPeluqueriaDetailResponse(
                solicitudPeluqueriaDto.id(),
                fromSolicitudToDetail(solicitudPeluqueriaDto.solicitud()),
                solicitudPeluqueriaDto.municipio(),
                solicitudPeluqueriaDto.direccion(),
                solicitudPeluqueriaDto.telefono());
    }

    public SolicitudProductoDetailResponse fromProductoToResponse(SolicitudProductoDto solicitudProductoDto) {
        if (solicitudProductoDto == null) {
            return null;
        }

        return new SolicitudProductoDetailResponse(
                solicitudProductoDto.id(),
                fromSolicitudToDetail(solicitudProductoDto.solicitud()),
                solicitudProductoDto.nombre(),
                solicitudProductoDto.precio(),
                solicitudProductoDto.duracion());
    }

    public SolicitudDetailResponse fromSolicitudToDetail(SolicitudDto solicitudDto) {
        if (solicitudDto == null) {
            return null;
        }

        return new SolicitudDetailResponse(
                solicitudDto.id(),
                UsuarioMapper.getInstance().fromDtoToDetail(solicitudDto.usuario()),
                solicitudDto.tipo(),
                solicitudDto.estado());
    }

    public SolicitudPeluqueriaDto fromRequestToPeluqueria(SolicitudPeluqueriaRequest solicitudPeluqueriaRequest) {
        if (solicitudPeluqueriaRequest == null) {
            return null;
        }

        return new SolicitudPeluqueriaDto(
                null,
                null,
                solicitudPeluqueriaRequest.municipio(),
                solicitudPeluqueriaRequest.direccion(),
                solicitudPeluqueriaRequest.telefono());
    }

    public SolicitudProductoDto fromRequestToProducto(SolicitudProductoRequest solicitudProductoRequest,
            CategoriaDto categoriaDto) {
        if (solicitudProductoRequest == null) {
            return null;
        }

        return new SolicitudProductoDto(
                null,
                null,
                categoriaDto,
                solicitudProductoRequest.nombre(),
                solicitudProductoRequest.precio(),
                solicitudProductoRequest.duracion());
    }
}
