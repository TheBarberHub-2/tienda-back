package com.fpmislata.daw.tienda.controller.mapper;

import java.util.ArrayList;
import java.util.List;

import com.fpmislata.daw.tienda.controller.webModel.request.PeluqueriaInsertRequest;
import com.fpmislata.daw.tienda.controller.webModel.request.PeluqueriaUpdateRequest;
import com.fpmislata.daw.tienda.controller.webModel.response.PeluqueriaDetailResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.PeluqueriaSummaryResponse;
import com.fpmislata.daw.tienda.controller.webModel.response.ProductoSummaryResponse;
import com.fpmislata.daw.tienda.domain.service.dto.PeluqueriaDto;
import com.fpmislata.daw.tienda.domain.service.dto.UsuarioDto;

public class PeluqueriaMapper {

    private static PeluqueriaMapper INSTANCE;

    private PeluqueriaMapper() {
    }

    public static PeluqueriaMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PeluqueriaMapper();
        }
        return INSTANCE;
    }

    public PeluqueriaDetailResponse fromDtoToDetail(PeluqueriaDto peluqueriaDto) {
        if (peluqueriaDto == null) {
            return null;
        }

        List<ProductoSummaryResponse> productos = new ArrayList<>();
        if (peluqueriaDto.productos() != null && !peluqueriaDto.productos().isEmpty()) {
            productos = peluqueriaDto.productos().stream()
                    .map(ProductoMapper.getInstance()::fromDtoToSummary)
                    .toList();
        }

        return new PeluqueriaDetailResponse(
                peluqueriaDto.usuario().email(),
                peluqueriaDto.usuario().nombre(),
                peluqueriaDto.municipio(),
                peluqueriaDto.direccion(),
                peluqueriaDto.telefono(),
                productos);
    }

    public PeluqueriaSummaryResponse fromDtoToSummary(PeluqueriaDto peluqueriaDto) {
        if (peluqueriaDto == null) {
            return null;
        }
        return new PeluqueriaSummaryResponse(
                peluqueriaDto.usuario().nombre(),
                peluqueriaDto.municipio(),
                peluqueriaDto.direccion(),
                peluqueriaDto.telefono());
    }

    public PeluqueriaDto fromInsertToDto(PeluqueriaInsertRequest peluqueriaInsertRequest, UsuarioDto usuarioDto) {
        if (peluqueriaInsertRequest == null) {
            return null;
        }
        return new PeluqueriaDto(
                null,
                usuarioDto,
                peluqueriaInsertRequest.municipio(),
                peluqueriaInsertRequest.direccion(),
                peluqueriaInsertRequest.telefono(),
                null);
    }

    public PeluqueriaDto fromUpdateToDto(PeluqueriaUpdateRequest peluqueriaUpdateRequest) {
        if (peluqueriaUpdateRequest == null) {
            return null;
        }
        return new PeluqueriaDto(
                null,
                null,
                peluqueriaUpdateRequest.municipio(),
                peluqueriaUpdateRequest.direccion(),
                peluqueriaUpdateRequest.telefono(),
                null);
    }
}
